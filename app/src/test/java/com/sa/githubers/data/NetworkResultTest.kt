package com.sa.githubers.data

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class NetworkResultTest {

    @Test
    fun `networkResult should return success when response is successful`() = runBlocking {
        // Given
        val responseBody = "Some success response"
        val mockResponse = Response.success(responseBody)
        val block: suspend () -> Response<String> = mockk()
        coEvery { block() } returns mockResponse

        // When
        val result = networkResult(block)

        // Then
        assertEquals(NetworkResult.Success(responseBody), result)
    }

    @Test
    fun `networkResult should return error when response is unsuccessful`() = runBlocking {
        // Given
        val errorCode = 404
        val errorResponseBody = "Not Found"
        val errorResponse = Response.error<String>(errorCode, errorResponseBody.toResponseBody())
        val block: suspend () -> Response<String> = mockk()
        coEvery { block() } returns errorResponse

        // When
        val result = networkResult(block)

        // Then
        assertEquals(NetworkResult.Error(errorCode, errorResponseBody), result)
    }

    @Test
    fun `networkResult should return error on exception`() = runBlocking {
        // Given
        val exceptionMessage = "Network error"
        val block: suspend () -> Response<String> = mockk()
        coEvery { block() } throws Exception(exceptionMessage)

        // When
        val result = networkResult(block)

        // Then
        assertEquals(NetworkResult.Error(null, exceptionMessage), result)
    }
}
