package com.sa.githubers.ui.mapper

import com.sa.githubers.FakeModels.Domain.fakeUserRepo
import com.sa.githubers.domain.model.UserRepoItem
import com.sa.githubers.domain.resourceloader.ResourceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserRepoDomainToUiMapperTest {

    private val mapper = UserRepoDomainToUiMapper()

    @Test
    fun `test mapFrom success state`() {
        // Arrange
        val userRepoItem = fakeUserRepo()
        val resourceState = ResourceState.Success(listOf(userRepoItem))

        // Act
        val result = mapper.mapFrom(resourceState)

        // Assert
        assertTrue(result is ResourceState.Success)
        val successResult = result as ResourceState.Success
        assertEquals(1, successResult.data.size)
        val uiModel = successResult.data[0]
        assertEquals(userRepoItem.id, uiModel.id)
        assertEquals(userRepoItem.name, uiModel.name)
        assertEquals(userRepoItem.private, uiModel.private)
        assertEquals(userRepoItem.description, uiModel.description)
    }

    @Test
    fun `test mapFrom error state`() {
        // Arrange
        val errorMessage = "error"
        val resourceState = ResourceState.Error<List<UserRepoItem>>(errorMessage)

        // Act
        val result = mapper.mapFrom(resourceState)

        // Assert
        assertTrue(result is ResourceState.Error)
        val errorResult = result as ResourceState.Error
        assertEquals(errorMessage, errorResult.message)
    }
}
