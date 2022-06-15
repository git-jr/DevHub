package com.paradoxo.devhub.webclient

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.paradoxo.devhub.model.toGitHubRepository
import com.paradoxo.devhub.model.toProfileUiState
import com.paradoxo.devhub.screen.ProfileUiState
import java.lang.Exception

class GitHubWebClient(private val service: ProfileGitHubService = RetrofitInit().profileGitHubService) {

    var uiState by mutableStateOf(ProfileUiState())
        private set

    suspend fun getProfilebyUser(user: String) {
        try {
            val profile = service.getProfilebyUser(user).toProfileUiState()
            val repositories = service.getRepositorybyUser(user).map { it.toGitHubRepository() }
            uiState = profile.copy(repositories = repositories)

        } catch (e: Exception) {
            Log.e("GitHubWebClient", "Falha ao buscar usuário: ${e}")
        }
    }
}