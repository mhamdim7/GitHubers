package com.sa.githubers.data.mapper

import com.sa.githubers.FakeModels.Data.fakeRepoResponse
import com.sa.githubers.data.NetworkResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserRepoDataToDomainMapperTest {

    private val mapper = UserRepoDataToDomainMapper()

    @Test
    fun `test mapFrom success state`() {
        // Arrange
        val userRepoResponse = fakeRepoResponse()
        val userRepoResponses = listOf(userRepoResponse)
        val networkResult = NetworkResult.Success(userRepoResponses)

        // Act
        val result = mapper.mapFrom(networkResult)

        // Assert
        assertTrue(result is NetworkResult.Success)
        val successResult = result as NetworkResult.Success
        assertEquals(1, successResult.data.size)
        val domainModel = successResult.data[0]
        assertEquals(userRepoResponse.id, domainModel.id)
        assertEquals(userRepoResponse.name, domainModel.name)
        assertEquals(userRepoResponse.private, domainModel.private)
        assertEquals(userRepoResponse.description, domainModel.description)
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
