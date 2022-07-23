package com.paradoxo.devhub.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.paradoxo.devhub.model.ConteudoRepository
import com.paradoxo.devhub.webclient.ConteudoWebClient


@Composable
fun InfoCardScreenConteudo(
    user: String,
    webClient: ConteudoWebClient = ConteudoWebClient(),
    onSearchClick: () -> Unit = {}
) {
    val uiState = webClient.uiState

    LaunchedEffect(null) {
        webClient.getConteudo()
    }

    Conteudo(uiState, onSearchClick)
}


@Composable
fun Conteudo(uiState: ConteudoUiState, onSearchClick: () -> Unit = {}) {

    LazyColumn {
        item {
            ConteudoHeader(uiState, onSearchClick = onSearchClick)
        }

        item {
            if (uiState.conteudos.isNotEmpty()) {
                Text(
                    text = "Repositórios",
                    Modifier.padding(start = 18.dp, top = 10.dp, bottom = 4.dp),
                    fontSize = 20.sp,
                    color = Color.Gray,
                )
            }
        }

        items(uiState.conteudos) { conteudo ->
            RepositoryItem(conteudo = conteudo)
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ConteudoHeader(userProfile: ConteudoUiState, onSearchClick: () -> Unit = {}) {
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
                onClick = { onSearchClick() },
                border = ChipDefaults.outlinedBorder,
                colors = ChipDefaults.outlinedChipColors(),
                modifier = Modifier
                    .offset(y = imageHeight / 2)
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp)
            ) {
                Text(
                    text = "Mudar usuário",
                )
            }



            Box(
                modifier = Modifier
                    .offset(y = imageHeight / 2, x = 20.dp)
                    .clip(CircleShape)
                    .background(
                        Color.White
                    )
                    .height(imageHeight)
                    .width(imageHeight)
                    .padding(4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = userProfile.image)
                        .crossfade(false)
                        .build(),
                    placeholder = painterResource(R.drawable.profile_placeholder),
                    contentDescription = "Avatar usuário",
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
            }


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
                userProfile.ranking,
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            )

        }
    }
}

@Composable
fun RepositoryItem(conteudo: ConteudoRepository) {
    Card(
        modifier = Modifier.padding(start = 18.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        elevation = 4.dp
    ) {
        Row() {
            val boxHeight = remember {
                120.dp
            }

            val imageHeight = remember {
                boxHeight
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        Color.White
                    )
                    .height(imageHeight)
                    .width(imageHeight)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = conteudo.image)
                        .crossfade(false)
                        .build(),
                    placeholder = painterResource(R.drawable.profile_placeholder),
                    contentDescription = "Imagem conteudo",
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    conteudo.title,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                )

                Text(
                    conteudo.image,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }


        }

    }

}


@Preview(showBackground = true)
@Composable
fun ConteudoPreview() {
    Conteudo(
        uiState = ConteudoUiState(
            user = "git-jr",
            image = "https://avatars.githubusercontent.com/u/35709152?v=4",
            name = "Junior",
            conteudos = listOf(
                ConteudoRepository(
                    title = "Conteudo for first test",
                    image = "Preview description 1"
                ),
                ConteudoRepository(
                    title = "Repository for second test",
                    image = "Preview description 2"
                )
            )
        )
    )

}

data class ConteudoUiState(
    val user: String = "",
    val image: String = "",
    val name: String = "",
    val ranking: String = "",
    val conteudos: List<ConteudoRepository> = emptyList()
)
