package com.sa.githubers.ui.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.sa.githubers.MockData.mockRepoEntryList
import com.sa.githubers.MockData.mockUserProfileResponse
import com.sa.githubers.data.model.UserDetailsResponse
import com.sa.githubers.data.model.UserRepoResponse
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserDetailUseCase
import com.sa.githubers.ui.model.UserRepoUiModel
import com.sa.githubers.ui.model.UserDetailsUiModel
import com.sa.githubers.ui.navigation.Routes
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var userDetailUseCase: UserDetailUseCase
    private val userLogin = "testUser"
    private val savedStateHandle = SavedStateHandle(mapOf(Routes.DETAILS_SCREEN_KEY to userLogin))
    private val error = "Some error message"

    @Before
    fun setUp() {
        userDetailUseCase = mockk(relaxed = true)
    }

    @Test
    fun `fetch user details - success`() = runBlocking {
        // Given

        val profileResponse = mockUserProfileResponse(userLogin)
        val userDetails = toUserDetailsUiModel(profileResponse)
        val userDetailsFlow = MutableStateFlow(
            ResourceState.Success(profileResponse)
        )

        coEvery { userDetailUseCase.getUserProfile(userLogin) } returns userDetailsFlow

        // When
        val viewModel = DetailsViewModel(savedStateHandle, userDetailUseCase)
        delay(1000)

        // Then
        val actualState = viewModel.userDetails.value
        assertEquals(ResourceState.Success(userDetails), actualState)
    }

    @Test
    fun `fetch user details - loading`() = runBlocking {
        // Given
        val userDetailsFlow = MutableStateFlow(
            ResourceState.Loading<UserDetailsResponse>()
        )
        coEvery { userDetailUseCase.getUserProfile(userLogin) } returns userDetailsFlow
        // When
        val viewModel = DetailsViewModel(savedStateHandle, userDetailUseCase)
        delay(1050)
        // Then
        val actualState = viewModel.userDetails.value
        assertTrue(actualState is ResourceState.Loading<UserDetailsUiModel>)
    }

    @Test
    fun `fetch user details - error`() = runBlocking {
        // Given
        val userDetailsFlow = MutableStateFlow<ResourceState<UserDetailsResponse>>(
            ResourceState.Error(error)
        )
        coEvery { userDetailUseCase.getUserProfile(userLogin) } returns userDetailsFlow
        // When
        val viewModel = DetailsViewModel(savedStateHandle, userDetailUseCase)
        delay(1050)
        // Then
        val actualState = viewModel.userDetails.value
        assertEquals(ResourceState.Error<UserDetailsUiModel>(error), actualState)
    }

    @Test
    fun `fetch user repos - success`() = runBlocking {
        // Given
        val repoList = mockRepoEntryList()
        val repoListUiModel = repoList.map { toRepoUiModel(it) }

        //WHen
        val repoListFlow = MutableStateFlow<ResourceState<List<UserRepoResponse>>>(
            ResourceState.Success(repoList)
        )
        coEvery { userDetailUseCase.getUserRepos(userLogin) } returns repoListFlow

        // THen
        val viewModel = DetailsViewModel(savedStateHandle, userDetailUseCase)
        delay(1000)

        val actualState = viewModel.repos.value
        assertEquals(ResourceState.Success(repoListUiModel), actualState)
    }

    @Test
    fun `fetch user repos - loading`() = runBlocking {
        // Given
        val repoListFlow = MutableStateFlow(
            ResourceState.Loading<List<UserRepoResponse>>()
        )
        coEvery { userDetailUseCase.getUserRepos(userLogin) } returns repoListFlow
        //When
        val viewModel = DetailsViewModel(savedStateHandle, userDetailUseCase)
        delay(1050)

        //Then
        val actualState = viewModel.repos.value
        assertTrue(actualState is ResourceState.Loading<List<UserRepoUiModel>>)
    }

    @Test
    fun `fetch user repos - error`() = runBlocking {
        // Given
        val repoListFlow = MutableStateFlow<ResourceState<List<UserRepoResponse>>>(
            ResourceState.Error(error)
        )

        coEvery { userDetailUseCase.getUserRepos(userLogin) } returns repoListFlow

        // When
        val viewModel = DetailsViewModel(savedStateHandle, userDetailUseCase)
        delay(1050)
        // Then
        val actualState = viewModel.repos.value
        assertEquals(ResourceState.Error<List<UserRepoUiModel>>(error), actualState)
    }


    // could be in a Mapper class
    private fun toUserDetailsUiModel(it: UserDetailsResponse) =
        UserDetailsUiModel(
            it.login,
            it.id,
            it.avatarUrl,
            it.location,
            it.name,
            it.bio,
            false
        )

    private fun toRepoUiModel(repo: UserRepoResponse) =
        UserRepoUiModel(
            repo.id,
            repo.name,
            repo.private,
            repo.description,
        )


}
