package com.sa.githubers.data.model

import com.squareup.moshi.Json


data class UsersResponse(
    @Json(name = "items")
    val items: List<UserEntry>
)

data class UserEntry(
    val login: String,
    val type: String,
    val id: Int,
    @Json(name = "avatar_url")
    val avatarUrl: String
)
