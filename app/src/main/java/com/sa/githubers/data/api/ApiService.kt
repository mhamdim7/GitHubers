package com.sa.githubers.data.api

import com.sa.githubers.data.model.UserDetailsResponse
import com.sa.githubers.data.model.UserRepoResponse
import com.sa.githubers.data.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    suspend fun getUserList(@Query("q") query: String): Response<UsersResponse>

    @GET("users/{login}")
    // TODO : maybe rename the login param to something more reasonable
    suspend fun getUserDetails(@Path("login") login: String): Response<UserDetailsResponse>

    @GET("users/{login}/repos")
    suspend fun getUserRepos(@Path("login") login: String): Response<List<UserRepoResponse>>

}