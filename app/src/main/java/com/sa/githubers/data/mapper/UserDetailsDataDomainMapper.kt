package com.sa.githubers.data.mapper

import com.sa.githubers.common.Mapper
import com.sa.githubers.data.NetworkResult
import com.sa.githubers.data.model.UserDetailsResponse
import com.sa.githubers.domain.model.UserDetails

class UserDetailsDataDomainMapper :
    Mapper<NetworkResult<UserDetailsResponse>, NetworkResult<UserDetails>> {
    override fun mapFrom(from: NetworkResult<UserDetailsResponse>) = from.mapSuccess {
        UserDetails(
            it.login,
            it.id,
            it.avatarUrl,
            it.location,
            it.name,
            it.bio,
            it.hireable ?: false
        )
    }

}