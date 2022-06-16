package com.paradoxo.devhub.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.paradoxo.devhub.R
import com.paradoxo.devhub.model.GitHubRepository
import com.paradoxo.devhub.webclient.GitHubWebClient


@Composable
fun InfoCardScreen(user: String, webClient: GitHubWebClient = GitHubWebClient()) {

    val uiState = webClient.uiState

    LaunchedEffect(null) {
        webClient.getProfilebyUser(user)
    }

    Profile(uiState, webClient)

}


@Composable
fun Profile(uiState: ProfileUiState, webClient: GitHubWebClient) {

    val visibilitySearchDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val userSearch: MutableState<String> = remember {
        mutableStateOf("")
    }

    SearchDialog(visibilitySearchDialog, onSearchClick = { user ->
        userSearch.value = user
    })


    if (userSearch.value.isNotBlank()) {

        val uiState = webClient.uiState
        LaunchedEffect(null) {
            webClient.getProfilebyUser(userSearch.value)

        }
        InfoCardScreen(user = userSearch.value)
    }

    LazyColumn {
        item {
            ProfileHeader(uiState, visibilitySearchDialog)
        }

        item {
            if (uiState.repositories.isNotEmpty()) {
                Text(
                    text = "Repositórios",
                    Modifier.padding(start = 18.dp, top = 12.dp, bottom = 4.dp),
                    fontSize = 20.sp,
                    color = Color.Gray,
                )
            }
        }

        items(uiState.repositories) { repo ->
            RepositoryItem(repo = repo)
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileHeader(userProfile: ProfileUiState, showSearchDialog: MutableState<Boolean>) {
    Column() {
        val boxHeight = remember {
            120.dp
        }

        val imageHeight = remember {
            boxHeight
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.DarkGray, shape = RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .height(boxHeight)

        ) {

            Chip(
                onClick = {
                    showSearchDialog.value = true
                },
                border = ChipDefaults.outlinedBorder,
                colors = ChipDefaults.outlinedChipColors(),
                modifier = Modifier
                    .offset(y = imageHeight / 2)
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp)
            ) {
                Text(
                    text = " Trocar usuario",
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = userProfile.image)
                    .crossfade(false)
                    .build(),
                placeholder = painterResource(R.drawable.profile_placeholder),
                contentDescription = "Avatar usuário",
                modifier = Modifier
                    .offset(y = imageHeight / 2, x = 20.dp)
                    .clip(CircleShape)
                    .border(4.dp, Color.White, CircleShape)
            )


        }

        Spacer(modifier = Modifier.height(imageHeight / 2))

        Column(
            Modifier
                .padding(18.dp, top = 4.dp)
                .fillMaxWidth(),

            ) {

            Text(userProfile.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(userProfile.user, fontSize = 20.sp, color = Color.Gray)
            Text(
                userProfile.bio,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            )

        }
    }
}

@Composable
fun RepositoryItem(repo: GitHubRepository) {
    Card(
        modifier = Modifier.padding(start = 18.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        elevation = 4.dp
    ) {

        Column {
            Text(
                repo.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .padding(8.dp),
                fontSize = 24.sp,
                color = Color.White
            )
            if (repo.description.isNotBlank()) {
                Text(
                    repo.description,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }

        }

    }

}

@Composable
fun SearchDialog(visible: MutableState<Boolean>, onSearchClick: (String) -> Unit) {

    val userNameSearch: MutableState<String> = remember {
        mutableStateOf(" ")
    }

    if (visible.value) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = "DevHub",
                    color = Color.Gray
                )
            },
            text = {
                OutlinedTextField(
                    value = userNameSearch.value,
                    onValueChange = { userNameSearch.value = it },
                    label = { Text("Buscar usuário") })
            },
            confirmButton = {
                TextButton(onClick = {
                    visible.value = false
                    onSearchClick(userNameSearch.value)
                }) {
                    Text(text = "Pesquisar", Modifier.padding(bottom = 16.dp))
                }
            },

            )

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileWithRepositoriesPreview() {
    Profile(
        uiState = ProfileUiState(
            user = "git-jr",
            image = "https://avatars.githubusercontent.com/u/35709152?v=4",
            name = "Junior",
            bio = "I'm a Paradoxo! YouTuber, developer, analyst and thinker. \nHave you ever experienced anything different today?",
            repositories = listOf(
                GitHubRepository(
                    name = "Repository for first test",
                    description = "Preview description 1"
                ),
                GitHubRepository(
                    name = "Repository for second test",
                    description = "Preview description 2"
                )
            )
        ),
        webClient = GitHubWebClient()
    )

}


data class ProfileUiState(
    val user: String = "",
    val image: String = "",
    val name: String = "",
    val bio: String = "",
    val repositories: List<GitHubRepository> = emptyList()
)
