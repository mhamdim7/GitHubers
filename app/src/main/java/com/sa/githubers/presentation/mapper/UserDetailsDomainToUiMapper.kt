package com.sa.githubers.presentation.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.domain.model.UserDetails
import com.sa.githubers.domain.resourceloader.ResourceState
import com.sa.githubers.presentation.model.UserDetailsUiModel

class UserDetailsDomainToUiMapper :
    Mapper<ResourceState<UserDetails>, ResourceState<UserDetailsUiModel>> {

    override fun mapFrom(from: ResourceState<UserDetails>) = from.mapSuccess {
        UserDetailsUiModel(
            it.login,
            it.id,
            it.avatarUrl,
            it.location,
            it.name,
            it.bio,
            it.hireable
        )
    }
}