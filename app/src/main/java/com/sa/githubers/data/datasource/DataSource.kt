package com.sa.githubers.data.datasource

import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.api.ApiService
import com.sa.githubers.data.mapper.UserDetailsDataToDomainMapper
import com.sa.githubers.data.mapper.UserListDataToDomainMapper
import com.sa.githubers.data.mapper.UserRepoDataToDomainMapper
import com.sa.githubers.data.networkResult
import com.sa.githubers.domain.model.UserDetails
import com.sa.githubers.domain.model.UserItem
import com.sa.githubers.domain.model.UserRepoItem
import javax.inject.Inject

interface DataSource {
    suspend fun getUserList(query: String): NetworkResult<List<UserItem>>
    suspend fun getUserDetails(login: String): NetworkResult<UserDetails>
    suspend fun getUserRepos(login: String): NetworkResult<List<UserRepoItem>>
}

class DataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val userListMapper: UserListDataToDomainMapper,
    private val userDetailsMapper: UserDetailsDataToDomainMapper,
    private val userRepoMapper: UserRepoDataToDomainMapper,
) : DataSource {

    override suspend fun getUserList(query: String) = networkResult {
        apiService.getUserList(query)
    }.run { userListMapper.mapFrom(this) }

    override suspend fun getUserDetails(login: String) = networkResult {
        apiService.getUserDetails(login)
    }.run { userDetailsMapper.mapFrom(this) }

    override suspend fun getUserRepos(login: String) = networkResult {
        apiService.getUserRepos(login)
    }.run { userRepoMapper.mapFrom(this) }

}

