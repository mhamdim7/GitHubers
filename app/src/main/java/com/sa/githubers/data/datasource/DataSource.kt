package com.sa.githubers.data.datasource

import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.api.ApiService
import com.sa.githubers.data.entity.ProfileResponse
import com.sa.githubers.data.entity.RepoEntry
import com.sa.githubers.data.entity.UsersResponse
import com.sa.githubers.data.networkResult
import javax.inject.Inject

interface DataSource {
    suspend fun getUsers(query: String): NetworkResult<UsersResponse>
    suspend fun getProfile(login: String): NetworkResult<ProfileResponse>
    suspend fun getRepos(login: String): NetworkResult<List<RepoEntry>>
}

class DataSourceImpl @Inject constructor(private val apiService: ApiService) : DataSource {

    override suspend fun getUsers(query: String) = networkResult {
        apiService.getUsers(query)
    }


    override suspend fun getProfile(login: String) = networkResult {
        apiService.getProfile(login)
    }

    override suspend fun getRepos(login: String) = networkResult {
        apiService.getRepos(login)
    }

}

