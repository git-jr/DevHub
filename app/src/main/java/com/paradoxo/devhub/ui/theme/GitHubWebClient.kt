package com.paradoxo.devhub.ui.theme

import android.util.Log
import com.paradoxo.devhub.webclient.ProfileGitHubService
import com.paradoxo.devhub.webclient.RetrofitInit
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GitHubWebClient(private val service: ProfileGitHubService = RetrofitInit().profileGitHubService) {

    fun getProfilebyUser(user: String) = flow {
        try {
            emit(service.getProfilebyUser(user))
        } catch (e: Exception) {
            Log.e("GitHubWebClient", "Falha ao busca usuário ${e.toString()}")
        }
    }

}