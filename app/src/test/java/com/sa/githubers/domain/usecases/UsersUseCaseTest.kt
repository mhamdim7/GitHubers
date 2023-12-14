package com.sa.githubers.domain.usecases

import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.entity.UsersResponse
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsersUseCaseTest {

    private lateinit var dataSource: DataSource
    private lateinit var usersUseCase: UsersUseCase
    private val query = "Query"

    @Before
    fun setUp() {
        dataSource = mockk()
        usersUseCase = UsersUseCase(dataSource)
    }


    @Test
    fun `getUsers success`() = runBlocking {
        // Given

        val mockUsersResponse = UsersResponse(listOf())
        coEvery { dataSource.getUsers(any()) } returns NetworkResult.Success(mockUsersResponse)

        // When
        val userProfileFlow = usersUseCase.getUsers(query)

        // then
        userProfileFlow.collect { result ->
            when (result) {

                is ResourceState.Success -> {
                    assertEquals(mockUsersResponse, result.data)
                }

                else -> {}
            }
        }
    }

    @Test
    fun `getUsers fail`() = runBlocking {
        // Given
        val errorMessage = "Error message"
        coEvery { dataSource.getUsers(any()) } returns NetworkResult.Error(401, errorMessage)

        // When
        val userListFlow = usersUseCase.getUsers(query)

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
