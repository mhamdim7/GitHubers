package com.sa.githubers.ui.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.sa.githubers.data.model.UserEntry
import com.sa.githubers.data.model.UsersResponse
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserListUseCase
import com.sa.githubers.ui.model.UserItemUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var userListUseCase: UserListUseCase
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        userListUseCase = mockk()
        viewModel = UsersViewModel(userListUseCase)
    }

    @Test
    fun `onSearchTextChange should fetch users when query length is greater than 1`() =
        runBlocking {
            // Given
            val query = "exampleQuery"
            val mockedUsersResponse = UsersResponse(
                listOf(
                    UserEntry("user1", "type1", 1, "url1"),
                    UserEntry("user2", "type2", 2, "url2")
                )
            )

            val mockedUiModel = mockedUsersResponse.items.map {
                UserItemUiModel(
                    it.login,
                    it.type,
                    it.id,
                    it.avatarUrl
                )
            }

            val mockedResponse = flow {
                emit(ResourceState.Success(mockedUsersResponse))
            }
            coEvery { userListUseCase.getUsers(query) } returns mockedResponse

            // When
            viewModel.onSearchTextChange(query)
            delay(1050) // Allow some time for the viewModel to process

            // Then
            assertTrue(viewModel.users.value is ResourceState.Success<List<UserItemUiModel>>)
            val successState = viewModel.users.value as ResourceState.Success<List<UserItemUiModel>>
            assertEquals(mockedUiModel, successState.data)
        }

    @Test
    fun `onSearchTextChange should not fetch users when query length is 1`(): Unit = runBlocking {
        // Given
        val query = "a"
        val mockedResponse = flow {
            emit(ResourceState.Success(UsersResponse(emptyList())))
        }
        coEvery { userListUseCase.getUsers(query) } returns mockedResponse

        // When
        viewModel.onSearchTextChange(query)
        delay(50)

        // Then
        assertTrue(viewModel.users.value is ResourceState.Idle<List<UserItemUiModel>>)
    }

}
