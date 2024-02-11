package com.sa.githubers.domain.usecases

import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.domain.resourceloader.resourceFlow
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(private val dataSource: DataSource) {

    suspend fun getUserProfile(login: String) = resourceFlow { dataSource.getUserDetails(login) }

    suspend fun getUserRepos(login: String) = resourceFlow { dataSource.getUserRepos(login) }
}
