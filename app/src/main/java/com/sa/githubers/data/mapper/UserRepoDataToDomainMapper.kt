package com.sa.githubers.data.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.model.UserRepoResponse
import com.sa.githubers.domain.model.UserRepoItem

class UserRepoDataToDomainMapper :
    Mapper<NetworkResult<List<UserRepoResponse>>, NetworkResult<List<UserRepoItem>>> {
    override fun mapFrom(from: NetworkResult<List<UserRepoResponse>>) =
        from.mapSuccess { response ->
            response.map {
                UserRepoItem(
                    it.id,
                    it.name,
                    it.private,
                    it.description,
                )
            }
        }
}