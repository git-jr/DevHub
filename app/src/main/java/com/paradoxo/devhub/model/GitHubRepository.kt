package com.paradoxo.devhub.model

data class GitHubRepository(
    val name: String = "",
    val description: String = ""
)

fun GitHubRepository.toGitHubRepository() = GitHubRepository(
    name = name,
    description = description ?: ""
)
