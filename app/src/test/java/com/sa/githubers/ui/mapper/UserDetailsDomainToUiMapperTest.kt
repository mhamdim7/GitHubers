package com.sa.githubers.ui.mapper

import com.sa.githubers.FakeModels.Domain.fakeUserDetails
import com.sa.githubers.domain.model.UserDetails
import com.sa.githubers.domain.resourceloader.ResourceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserDetailsDomainToUiMapperTest {

    private val mapper = UserDetailsDomainToUiMapper()

    @Test
    fun `test mapFrom success state`() {
        // Arrange
        val userDetails = fakeUserDetails()
        val resourceState = ResourceState.Success(userDetails)

        // Act
        val result = mapper.mapFrom(resourceState)

        // Assert
        assertTrue(result is ResourceState.Success)
        val successResult = result as ResourceState.Success
        val uiModel = successResult.data
        assertEquals(userDetails.login, uiModel.login)
        assertEquals(userDetails.id, uiModel.id)
        assertEquals(userDetails.avatarUrl, uiModel.avatarUrl)
        assertEquals(userDetails.location, uiModel.location)
        assertEquals(userDetails.name, uiModel.name)
        assertEquals(userDetails.bio, uiModel.bio)
        assertEquals(userDetails.hireable, uiModel.hireable)
    }

    @Test
    fun `test mapFrom error state`() {
        // Arrange
        val error = "someError"
        val resourceState = ResourceState.Error<UserDetails>(error)

        // Act
        val result = mapper.mapFrom(resourceState)

        // Assert
        assertTrue(result is ResourceState.Error)
        val errorResult = result as ResourceState.Error
        assertEquals(error, errorResult.message)
    }
}
