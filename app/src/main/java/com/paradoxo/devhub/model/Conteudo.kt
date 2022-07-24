package com.paradoxo.devhub.model

import com.paradoxo.devhub.screen.ConteudoUiState

data class Conteudo(
    val id : String,
    val login: String,
    val name: String,
    val avatar_url: String,
    val ranking: String
)



fun Conteudo.toConteudoUiState(): ConteudoUiState {
    return ConteudoUiState(
        id = id,
        user = login,
        image = avatar_url,
        name = name,
        ranking = ranking
    )
}