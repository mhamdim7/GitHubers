package com.sa.githubers.ui

data class UserDetailsUiModel(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val location: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val hireable: Boolean
)

data class RepoItemUiModel(
    val id: Int,
    val name: String,
    val private: Boolean,
    val description: String? = null
)