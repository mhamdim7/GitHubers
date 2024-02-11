package com.sa.githubers.presentation.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.domain.model.UserItem
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.presentation.model.UserItemUiModel

class UserListDomainToUiMapper :
    Mapper<ResourceState<List<UserItem>>, ResourceState<List<UserItemUiModel>>> {
    override fun mapFrom(from: ResourceState<List<UserItem>>) = from.mapSuccess {
        it.map { UserItemUiModel(it.login, it.type, it.id, it.avatarUrl) }
    }
}