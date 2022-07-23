package com.paradoxo.devhub.webclient

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.paradoxo.devhub.model.toConteudoRepository
import com.paradoxo.devhub.screen.ConteudoUiState

class ConteudoWebClient(private val service: ConteudoGitHubService = RetrofitInit().conteudoGitHubService) {

    var uiState by mutableStateOf(ConteudoUiState())
        private set

    suspend fun getConteudo() {
        try {
            val conteudos = service.getLinguagens().map { it.toConteudoRepository() }
            val profile = ConteudoUiState(conteudos = conteudos);
            uiState =  profile.copy();

        } catch (e: Exception) {
            Log.e("ConteudoWebClient", "Falha ao buscar conteudos${e}")
        }
    }
}