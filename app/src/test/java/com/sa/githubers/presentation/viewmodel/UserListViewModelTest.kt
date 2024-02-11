package com.sa.githubers.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.sa.githubers.FakeModels.Domain.fakeUserItem
import com.sa.githubers.domain.model.UserItem
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.domain.usecases.UserListUseCase
import com.sa.githubers.presentation.viewmodel.UserListViewModel
import com.sa.githubers.presentation.mapper.UserListDomainToUiMapper
import com.sa.githubers.presentation.model.UserItemUiModel
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
class UserListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var userListUseCase: UserListUseCase
    private lateinit var viewModel: UserListViewModel
    private val mapper = UserListDomainToUiMapper()

    @Before
    fun setup() {
        userListUseCase = mockk()
        viewModel = UserListViewModel(userListUseCase, mapper)
    }

    @Test
    fun `onSearchTextChange should fetch users when query length is greater than 1`() =
        runBlocking {
            // Given
            val query = "exampleQuery"
            val userList = ResourceState.Success(listOf(fakeUserItem()))
            val userListFlow = MutableStateFlow(userList)
            coEvery { userListUseCase.getUsers(query) } returns userListFlow

            // When
            viewModel.onSearchTextChange(query)
            delay(1050) // Allow some time for the viewModel to process

            // Then
            val successState = viewModel.users.value
            val expected = mapper.mapFrom(userList)
            assertEquals(expected, successState)
        }

    @Test
    fun `onSearchTextChange should not fetch users when query length is 1`(): Unit = runBlocking {
        // Given
        val query = "a"
        val userList = ResourceState.Success(listOf<UserItem>())
        val userListFlow = MutableStateFlow(userList)
        coEvery { userListUseCase.getUsers(query) } returns userListFlow

        // When
        viewModel.onSearchTextChange(query)
        delay(50)

        // Then
        assertTrue(viewModel.users.value is ResourceState.Idle<List<UserItemUiModel>>)
    }

}
