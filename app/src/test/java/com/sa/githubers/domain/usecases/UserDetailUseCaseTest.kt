package com.sa.githubers.domain.usecases

import com.sa.githubers.FakeModels.Domain.fakeUserDetails
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.domain.model.UserRepoItem
import com.sa.githubers.domain.resourceloader.ResourceState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserDetailUseCaseTest {

    private lateinit var dataSource: DataSource
    private lateinit var userDetailUseCase: UserDetailUseCase
    private val login = "someLogin"
    private val errorMessage = "Error message"

    @Before
    fun setUp() {
        dataSource = mockk()
        userDetailUseCase = UserDetailUseCase(dataSource)
    }

    @Test
    fun `getUserProfile success`() = runBlocking {
        // given
        val userDetailsNetworkResult = fakeUserDetails(login)
        coEvery { dataSource.getUserDetails(login) } returns NetworkResult.Success(
            userDetailsNetworkResult
        )

        // when
        val userProfileFlow = userDetailUseCase.getUserProfile(login)

        // then
        userProfileFlow.collect { result ->
            when (result) {

                is ResourceState.Success -> {
                    assertEquals(userDetailsNetworkResult, result.data)
                }

                else -> {}
            }
        }
    }

    @Test
    fun `getUserProfile error`() = runBlocking {
        // Given
        coEvery { dataSource.getUserDetails(login) } returns NetworkResult.Error(404, errorMessage)

        // When
        val userProfileFlow = userDetailUseCase.getUserProfile(login)

        // Then
        userProfileFlow.collect { result ->
            when (result) {

                is ResourceState.Error -> {
                    assertEquals(errorMessage, result.message)
                }

                else -> {}
            }
        }
    }

    @Test
    fun `getUserRepos success`() = runBlocking {
        // given
        val repoList = listOf<UserRepoItem>()
        coEvery { dataSource.getUserRepos(login) } returns NetworkResult.Success(repoList)

        // when
        val repoListFlow = userDetailUseCase.getUserRepos(login)

        // then
        repoListFlow.collect { result ->
            when (result) {

                is ResourceState.Success -> {
                    assertEquals(repoList, result.data)
                }

                else -> {}
            }
        }
    }

    @Test
    fun `getUserRepos error`() = runBlocking {
        // Given
        coEvery { dataSource.getUserRepos(login) } returns NetworkResult.Error(404, errorMessage)

        // When
        val repoListFlow = userDetailUseCase.getUserRepos(login)

        // Then
        repoListFlow.collect { result ->
            when (result) {

                is ResourceState.Error -> {
                    assertEquals(errorMessage, result.message)
                }

                else -> {}
            }
        }
    }
}
