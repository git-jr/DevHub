package com.paradoxo.devhub.model

data class ConteudoRepository(
    val id: String,
    val title: String = "",
    val image: String = "",
    val ranking: String = "",
)

fun ConteudoRepository.toConteudoRepository() = ConteudoRepository(
    id = id,
    title = title,
    image = image,
    ranking = ranking

)
