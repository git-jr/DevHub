package com.paradoxo.devhub.model

import com.paradoxo.devhub.screen.ProfileUiState

data class ProfileGitHub(
    val login: String,
    val name: String,
    val avatar_url: String,
    val bio: String
)


fun ProfileGitHub.toProfileUiState(): ProfileUiState {
    return ProfileUiState(
        user = login,
        image = avatar_url,
        name = name,
        bio = bio
    )
}