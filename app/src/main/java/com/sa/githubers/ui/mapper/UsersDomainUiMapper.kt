package com.sa.githubers.ui.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.domain.model.UserItem
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.ui.model.UserItemUiModel

class UsersDomainUiMapper :
    Mapper<ResourceState<List<UserItem>>, ResourceState<List<UserItemUiModel>>> {
    override fun mapFrom(from: ResourceState<List<UserItem>>) = from.mapSuccess {
        it.map { UserItemUiModel(it.login, it.type, it.id, it.avatarUrl) }
    }
}