package com.sa.githubers.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.sa.githubers.FakeModels.Domain.fakeUserDetails
import com.sa.githubers.FakeModels.Domain.fakeUserRepo
import com.sa.githubers.domain.model.UserDetails
import com.sa.githubers.domain.model.UserRepoItem
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserDetailUseCase
import com.sa.githubers.presentation.viewmodel.DetailsViewModel
import com.sa.githubers.presentation.mapper.UserDetailsDomainToUiMapper
import com.sa.githubers.presentation.mapper.UserRepoDomainToUiMapper
import com.sa.githubers.presentation.model.UserDetailsUiModel
import com.sa.githubers.presentation.model.UserRepoUiModel
import com.sa.githubers.presentation.ui.navigation.Routes
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
    private var userDetailsUiMapper = UserDetailsDomainToUiMapper()
    private var userRepoUiMapper = UserRepoDomainToUiMapper()

    @Before
    fun setUp() {
        userDetailUseCase = mockk(relaxed = true)
    }

    @Test
    fun `fetch user details - success`() = runBlocking {
        // Given
        val userDetails = ResourceState.Success(fakeUserDetails(userLogin))
        val userDetailsFlow = MutableStateFlow(userDetails)

        coEvery { userDetailUseCase.getUserProfile(userLogin) } returns userDetailsFlow

        // When
        val viewModel = viewModel()
        delay(1000)

        // Then
        val expected = userDetailsUiMapper.mapFrom(userDetails)
        val actualState = viewModel.userDetails.value
        assertEquals(expected, actualState)
    }

    @Test
    fun `fetch user details - loading`() = runBlocking {
        // Given
        val userDetailsFlow = MutableStateFlow(
            ResourceState.Loading<UserDetails>()
        )
        coEvery { userDetailUseCase.getUserProfile(userLogin) } returns userDetailsFlow
        // When
        val viewModel = viewModel()
        delay(1050)
        // Then
        val actualState = viewModel.userDetails.value
        assertTrue(actualState is ResourceState.Loading<UserDetailsUiModel>)
    }

    @Test
    fun `fetch user details - error`() = runBlocking {
        // Given
        val userDetailsFlow = MutableStateFlow<ResourceState<UserDetails>>(
            ResourceState.Error(error)
        )
        coEvery { userDetailUseCase.getUserProfile(userLogin) } returns userDetailsFlow
        // When
        val viewModel = viewModel()
        delay(1050)
        // Then
        val actualState = viewModel.userDetails.value
        assertEquals(ResourceState.Error<UserDetailsUiModel>(error), actualState)
    }

    @Test
    fun `fetch user repos - success`() = runBlocking {
        // Given
        val repoList = ResourceState.Success(listOf(fakeUserRepo()))

        //When
        val repoListFlow = MutableStateFlow(repoList)
        coEvery { userDetailUseCase.getUserRepos(userLogin) } returns repoListFlow

        // Then
        val viewModel = viewModel()
        delay(1000)

        val actualState = viewModel.repos.value
        val expected = userRepoUiMapper.mapFrom(repoList)
        assertEquals(expected, actualState)
    }

    @Test
    fun `fetch user repos - loading`() = runBlocking {
        // Given
        val repoListFlow = MutableStateFlow(
            ResourceState.Loading<List<UserRepoItem>>()
        )
        coEvery { userDetailUseCase.getUserRepos(userLogin) } returns repoListFlow
        //When
        val viewModel = viewModel()
        delay(1050)

        //Then
        val actualState = viewModel.repos.value
        assertTrue(actualState is ResourceState.Loading<List<UserRepoUiModel>>)
    }

    @Test
    fun `fetch user repos - error`() = runBlocking {
        // Given
        val repoListFlow = MutableStateFlow<ResourceState<List<UserRepoItem>>>(
            ResourceState.Error(error)
        )

        coEvery { userDetailUseCase.getUserRepos(userLogin) } returns repoListFlow

        // When
        val viewModel = viewModel()
        delay(1050)
        // Then
        val actualState = viewModel.repos.value
        assertEquals(ResourceState.Error<List<UserRepoUiModel>>(error), actualState)
    }

    private fun viewModel() = DetailsViewModel(
        savedStateHandle,
        userDetailUseCase,
        userDetailsUiMapper,
        userRepoUiMapper
    )
}
