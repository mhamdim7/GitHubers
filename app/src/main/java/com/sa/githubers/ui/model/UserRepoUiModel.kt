package com.sa.githubers.ui.model

data class UserRepoUiModel(
    val id: Int,
    val name: String,
    val private: Boolean,
    val description: String? = null
)