package com.paradoxo.devhub.model

import com.paradoxo.devhub.screen.ConteudoUiState

data class ConteudoGitHub(
    val login: String,
    val name: String,
    val avatar_url: String,
    val ranking: String
)


fun ConteudoGitHub.toConteudoUiState(): ConteudoUiState {
    return ConteudoUiState(
        user = login,
        image = avatar_url,
        name = name,
        ranking = ranking
    )
}