package com.sa.githubers

import com.sa.githubers.data.model.UserDetailsResponse
import com.sa.githubers.data.model.UserEntry
import com.sa.githubers.data.model.UserRepoResponse
import com.sa.githubers.data.model.UsersResponse
import com.sa.githubers.domain.model.UserDetails
import com.sa.githubers.domain.model.UserItem
import com.sa.githubers.domain.model.UserRepoItem

object FakeModels {
    object Data {
        fun fakeUserDetailsResponse(login: String="login") = UserDetailsResponse(
            login = login,
            id = 1,
            avatarUrl = "someUrl",
            location = "someLocation",
            name = "someName",
            bio = "someBio",
            false
        )

        fun fakeUsersResponse(login: String="login") = UsersResponse(
            listOf(
                UserEntry(
                    login,
                    "someType",
                    1,
                    "someUrl"
                )
            )
        )

        fun fakeRepoResponse(): UserRepoResponse = UserRepoResponse(
            id = 1,
            name = "someName",
            private = false,
            description = "someDescription",
        )
    }

    object Domain {

        fun fakeUserDetails(login: String = "someLogin") = UserDetails(
            login = login,
            id = 1,
            avatarUrl = "someUrl",
            location = "someLocation",
            name = "someName",
            bio = "someBio",
            false
        )

        fun fakeUserRepo() = UserRepoItem(
            id = 1,
            name = "someName",
            private = false,
            description = "someDescription",
        )

        fun fakeUserItem(login: String = "someLogin") = UserItem(
            login,
            "someType",
            1,
            "someUrl"
        )
    }
}