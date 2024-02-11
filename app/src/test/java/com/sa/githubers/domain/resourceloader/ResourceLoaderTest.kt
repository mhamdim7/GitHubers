package com.sa.githubers.domain.resourceloader

import com.sa.githubers.FakeModels.Domain.fakeUserItem
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.data.model.UsersResponse
import com.sa.githubers.domain.model.UserItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ResourceLoaderTest {

    private lateinit var dataSource: DataSource
    private val userId = "someUserId"
    private val errorMessage = "Network error"

    @Before
    fun setUp() {
        dataSource = mockk<DataSource>()
    }

    @Test
    fun `test loadResource success`() = runBlocking {

        val userList = listOf(fakeUserItem())
        val mockResponse = NetworkResult.Success(userList)

        coEvery { dataSource.getUserList(userId) }
            .returns(mockResponse)

        val resourceFlow = resourceFlow {
            dataSource.getUserList(userId)
        }

        val result = resourceFlow.toList()
        assertTrue(result[0] is ResourceState.Loading<List<UserItem>>)
        assertEquals(ResourceState.Success(userList), result[1])
    }

    @Test
    fun `test loadResource failure`() = runBlocking {

        coEvery { dataSource.getUserList(userId) }
            .throws(Exception(errorMessage))

        val resourceFlow = resourceFlow {
            dataSource.getUserList(userId)
        }

        val result = resourceFlow.toList()
        assertTrue(result[0] is ResourceState.Loading<List<UserItem>>)
        assertEquals(ResourceState.Error<UsersResponse>(errorMessage), result[1])
    }
}
