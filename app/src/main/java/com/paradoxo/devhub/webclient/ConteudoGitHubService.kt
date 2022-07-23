package com.paradoxo.devhub.webclient

import com.paradoxo.devhub.model.ConteudoGitHub
import com.paradoxo.devhub.model.ConteudoRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface ConteudoGitHubService {

    @GET("/linguagens/{user}")
    suspend fun getConteudobyId(@Path("id") id: String): ConteudoGitHub

    @GET("/linguagens")
    suspend fun getLinguagens(): List<ConteudoRepository>
}