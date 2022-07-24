package com.paradoxo.devhub.webclient

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.paradoxo.devhub.model.toConteudoRepository
import com.paradoxo.devhub.screen.ConteudoUiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConteudoWebClient(urlApi: String) {
    private val service: ConteudoGitHubService = RetrofitInit(urlApi).conteudoGitHubService

    var uiState by mutableStateOf(ConteudoUiState())
        private set

    suspend fun getConteudo() {
        try {
            val conteudos = service.getLinguagens().map { it.toConteudoRepository() }
            val profile = ConteudoUiState(conteudos = conteudos);
            uiState = profile.copy();

        } catch (e: Exception) {
            Log.e("ConteudoWebClient", "Falha ao buscar conteudos${e}")
        }
    }

    fun votar(id: String) {

        val votar = service.votar(id)
        votar.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("callbackVoto", t.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i("callbackVoto", "${response.code()}")
            }

        })

    }
}