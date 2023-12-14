package com.sa.githubers.data.entity

import com.squareup.moshi.Json

data class ProfileResponse(
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

data class RepoEntry(
    val id: Int,
    val name: String,
    val private: Boolean,
    val description: String? = null
)