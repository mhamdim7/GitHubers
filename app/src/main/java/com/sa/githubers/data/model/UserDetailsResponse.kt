package com.sa.githubers.data.model

import com.squareup.moshi.Json

data class UserDetailsResponse(
    val login: String,
    val id: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "location")
    val location: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val hireable: Boolean? = null
)

