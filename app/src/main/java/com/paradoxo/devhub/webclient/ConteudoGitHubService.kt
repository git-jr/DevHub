package com.paradoxo.devhub.webclient

import android.database.Observable
import com.paradoxo.devhub.model.Conteudo
import com.paradoxo.devhub.model.ConteudoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ConteudoGitHubService {

    @GET("/linguagens/{user}")
    suspend fun getConteudobyId(@Path("id") id: String): Conteudo

    @GET("/linguagens")
    suspend fun getLinguagens(): List<ConteudoRepository>


    @PATCH("/vote/{id}")
    fun votar(@Path("id") id: String): Call<Void>


}