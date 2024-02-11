package com.sa.githubers.presentation.model

data class UserRepoUiModel(
    val id: Int,
    val name: String,
    val private: Boolean,
    val description: String? = null
)