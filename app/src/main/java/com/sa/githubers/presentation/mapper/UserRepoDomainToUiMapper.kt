package com.sa.githubers.presentation.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.domain.model.UserRepoItem
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.presentation.model.UserRepoUiModel

class UserRepoDomainToUiMapper :
    Mapper<ResourceState<List<UserRepoItem>>, ResourceState<List<UserRepoUiModel>>> {
    override fun mapFrom(from: ResourceState<List<UserRepoItem>>) =
        from.mapSuccess { response ->
            response.map {
                UserRepoUiModel(
                    it.id,
                    it.name,
                    it.private,
                    it.description,
                )
            }
        }
}