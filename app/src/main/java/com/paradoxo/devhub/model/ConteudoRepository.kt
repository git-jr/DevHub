package com.paradoxo.devhub.model

data class ConteudoRepository(
    val title: String = "",
    val image: String = "",
    val ranking: String = "",
)

fun ConteudoRepository.toConteudoRepository() = ConteudoRepository(
    title = title,
    image = image,
    ranking = ranking

)
