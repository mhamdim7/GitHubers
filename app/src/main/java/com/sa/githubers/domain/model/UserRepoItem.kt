package com.sa.githubers.domain.model

data class UserRepoItem(
    val id: Int,
    val name: String,
    val private: Boolean,
    val description: String? = null
)