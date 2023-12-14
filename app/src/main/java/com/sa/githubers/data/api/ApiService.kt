package com.sa.githubers.data.api

import com.sa.githubers.data.entity.ProfileResponse
import com.sa.githubers.data.entity.RepoEntry
import com.sa.githubers.data.entity.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    suspend fun getUsers(@Query("q") query: String): Response<UsersResponse>

    @GET("users/{login}")
    suspend fun getProfile(@Path("login") login: String): Response<ProfileResponse>

    @GET("users/{login}/repos")
    suspend fun getRepos(@Path("login") login: String): Response<List<RepoEntry>>

}