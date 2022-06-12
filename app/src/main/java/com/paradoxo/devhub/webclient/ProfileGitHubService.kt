package com.paradoxo.devhub.webclient

import com.paradoxo.devhub.model.ProfileGitHub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileGitHubService {

    @GET("/users/{user}")
    suspend fun getProfilebyUser(@Path("user") user: String): ProfileGitHub
}