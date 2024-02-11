package com.sa.githubers.ui.model

data class UserDetailsUiModel(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val location: String? = null,
    val name: String? = null,
    val bio: String? = null,
    val hireable: Boolean
)