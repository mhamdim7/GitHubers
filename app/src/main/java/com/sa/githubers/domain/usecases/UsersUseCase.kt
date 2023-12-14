package com.sa.githubers.domain.usecases

import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.domain.resourceloader.resourceFlow
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val dataSource: DataSource) {

    suspend fun getUsers(query: String) = resourceFlow { dataSource.getUsers(query) }

}