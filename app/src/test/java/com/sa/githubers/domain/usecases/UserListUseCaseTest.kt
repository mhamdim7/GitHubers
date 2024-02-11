package com.sa.githubers.domain.usecases

import com.sa.githubers.FakeModels.Domain.fakeUserItem
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.datasource.DataSourceImpl
import com.sa.githubers.domain.resourceloader.ResourceState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserListUseCaseTest {

    private lateinit var dataSource: DataSourceImpl
    private lateinit var userListUseCase: UserListUseCase
    private val query = "Query"

    @Before
    fun setUp() {
        dataSource = mockk()
        userListUseCase = UserListUseCase(dataSource)
    }

    @Test
    fun `getUsers success`() = runBlocking {
        // Given

        val userListNetworkResult = listOf(fakeUserItem())
        coEvery { dataSource.getUserList(any()) } returns NetworkResult.Success(
            userListNetworkResult
        )

        // When
        val userProfileFlow = userListUseCase.getUsers(query)

        // then
        userProfileFlow.collect { result ->
            when (result) {

                is ResourceState.Success -> {
                    assertEquals(userListNetworkResult, result.data)
                }

                else -> {}
            }
        }
    }

    @Test
    fun `getUsers fail`() = runBlocking {
        // Given
        val errorMessage = "Error message"
        coEvery { dataSource.getUserList(any()) } returns NetworkResult.Error(401, errorMessage)

        // When
        val userListFlow = userListUseCase.getUsers(query)

        // then
        userListFlow.collect { result ->
            when (result) {

                is ResourceState.Error -> {
                    assertEquals(errorMessage, result.message)
                }

                else -> {}
            }
        }
    }
}
