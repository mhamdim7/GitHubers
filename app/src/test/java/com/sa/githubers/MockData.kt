package com.sa.githubers

import com.sa.githubers.data.entity.ProfileResponse
import com.sa.githubers.data.entity.RepoEntry

object MockData {
    fun mockProfileResponse(login: String) = ProfileResponse(
        login = login,
        id = 1,
        avatarUrl = "someUrl",
        location = "someLocation",
        name = "someName",
        bio = "someBio"
    )

    fun mockUserProfileResponse(login: String) = ProfileResponse(
        login = login,
        id = 1,
        avatarUrl = "someUrl",
        location = "someLocation",
        name = "someName",
        bio = "someBio"
    )

    fun mockRepoEntryList(): List<RepoEntry> = listOf(
        RepoEntry(
            id = 1,
            name = "someName",
            private = false,
            description = "someDescription",
        )
    )
}