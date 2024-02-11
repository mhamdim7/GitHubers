package com.sa.githubers.data.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.model.UsersResponse
import com.sa.githubers.domain.model.UserItem


class UserListDataToDomainMapper :
    Mapper<NetworkResult<UsersResponse>, NetworkResult<List<UserItem>>> {
    override fun mapFrom(from: NetworkResult<UsersResponse>) = from.mapSuccess { response ->
        response.items.map { UserItem(it.login, it.type, it.id, it.avatarUrl) }
    }
}
