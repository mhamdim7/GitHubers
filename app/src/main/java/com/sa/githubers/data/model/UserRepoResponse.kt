package com.sa.githubers.data.model

data class UserRepoResponse(
    val id: Int,
    val name: String,
    val private: Boolean,
    val description: String? = null
)