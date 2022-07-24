package com.paradoxo.devhub.webclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit(urlBase: String) {

    private val retrofit = Retrofit.Builder()
        //.baseUrl("https://api.github.com/")
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val conteudoGitHubService get() = retrofit.create(ConteudoGitHubService::class.java)
    // val profileGitHubService get() = retrofit.create(ProfileGitHubService::class.java)
}