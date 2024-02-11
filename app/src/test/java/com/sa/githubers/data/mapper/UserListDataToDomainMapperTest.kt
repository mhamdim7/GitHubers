package com.sa.githubers.data.mapper

import com.sa.githubers.FakeModels.Data.fakeUsersResponse
import com.sa.githubers.data.NetworkResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserListDataToDomainMapperTest {

    private val mapper = UserListDataToDomainMapper()

    @Test
    fun `test mapFrom success state`() {
        // Arrange
        val usersResponse = fakeUsersResponse()
        val networkResult = NetworkResult.Success(usersResponse)

        // Act
        val result = mapper.mapFrom(networkResult)

        // Assert
        val expected = usersResponse.items[0]
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        assertEquals(1, successResult.data.size)
        val domainModel = successResult.data[0]
        assertEquals(expected.login, domainModel.login)
        assertEquals(expected.type, domainModel.type)
        assertEquals(expected.id, domainModel.id)
        assertEquals(expected.avatarUrl, domainModel.avatarUrl)
    }

    @Test
    fun `test mapFrom error state`() {
        // Arrange
        val networkResult = NetworkResult.Error(1, "error")

        // Act
        val result = mapper.mapFrom(networkResult)

        // Assert
        assertTrue(result is NetworkResult.Error)
        val errorResult = result as NetworkResult.Error
        assertEquals(networkResult, errorResult)
    }
}
