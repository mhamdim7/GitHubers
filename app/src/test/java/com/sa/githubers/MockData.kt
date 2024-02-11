package com.sa.githubers

import com.sa.githubers.data.model.UserDetailsResponse
import com.sa.githubers.data.model.UserRepoResponse

object MockData {
    fun mockProfileResponse(login: String) = UserDetailsResponse(
        login = login,
        id = 1,
        avatarUrl = "someUrl",
        location = "someLocation",
        name = "someName",
        bio = "someBio"
    )

    fun mockUserProfileResponse(login: String) = UserDetailsResponse(
        login = login,
        id = 1,
        avatarUrl = "someUrl",
        location = "someLocation",
        name = "someName",
        bio = "someBio"
    )

    fun mockRepoEntryList(): List<UserRepoResponse> = listOf(
        UserRepoResponse(
            id = 1,
            name = "someName",
            private = false,
            description = "someDescription",
        )
    )
}