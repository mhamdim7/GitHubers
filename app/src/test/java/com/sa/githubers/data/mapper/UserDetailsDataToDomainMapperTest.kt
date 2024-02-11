package com.sa.githubers.data.mapper

import com.sa.githubers.FakeModels.Data.fakeUserDetailsResponse
import com.sa.githubers.data.NetworkResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserDetailsDataToDomainMapperTest {

    private val mapper = UserDetailsDataToDomainMapper()

    @Test
    fun `test mapFrom success state`() {
        // Arrange
        val userDetailsResponse = fakeUserDetailsResponse()
        val networkResult = NetworkResult.Success(userDetailsResponse)

        // Act
        val result = mapper.mapFrom(networkResult)

        // Assert
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        val domainModel = successResult.data
        assertEquals(userDetailsResponse.login, domainModel.login)
        assertEquals(userDetailsResponse.id, domainModel.id)
        assertEquals(userDetailsResponse.avatarUrl, domainModel.avatarUrl)
        assertEquals(userDetailsResponse.location, domainModel.location)
        assertEquals(userDetailsResponse.name, domainModel.name)
        assertEquals(userDetailsResponse.bio, domainModel.bio)
        assertEquals(userDetailsResponse.hireable, domainModel.hireable)
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
