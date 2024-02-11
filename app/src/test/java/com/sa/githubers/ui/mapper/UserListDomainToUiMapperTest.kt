package com.sa.githubers.ui.mapper

import com.sa.githubers.FakeModels.Domain.fakeUserItem
import com.sa.githubers.domain.model.UserItem
import com.sa.githubers.domain.resourceloader.ResourceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserListDomainToUiMapperTest {

    private val mapper = UserListDomainToUiMapper()

    @Test
    fun `test mapFrom success state`() {
        // Arrange
        val userItem = fakeUserItem()
        val resourceState = ResourceState.Success(listOf(userItem))

        // Act
        val result = mapper.mapFrom(resourceState)

        // Assert
        assertTrue(result is ResourceState.Success)
        val successResult = result as ResourceState.Success
        assertEquals(1, successResult.data.size)
        val uiModel = successResult.data[0]
        assertEquals(userItem.login, uiModel.login)
        assertEquals(userItem.type, uiModel.type)
        assertEquals(userItem.id, uiModel.id)
        assertEquals(userItem.avatarUrl, uiModel.avatarUrl)
    }

    @Test
    fun `test mapFrom error state`() {
        // Arrange
        val error = "someError"
        val resourceState = ResourceState.Error<List<UserItem>>(error)

        // Act
        val result = mapper.mapFrom(resourceState)

        // Assert
        assertTrue(result is ResourceState.Error)
        val errorResult = result as ResourceState.Error
        assertEquals(error, errorResult.message)
    }
}
