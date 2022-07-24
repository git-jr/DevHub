package com.paradoxo.devhub.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.paradoxo.devhub.ConteudoActivity
import com.paradoxo.devhub.R
import com.paradoxo.devhub.model.ConteudoRepository
import com.paradoxo.devhub.webclient.ConteudoWebClient


@Composable
fun InfoCardScreenConteudo(
    urlApi: String,
    webClient: ConteudoWebClient = ConteudoWebClient(urlApi),
    onSearchClick: () -> Unit = {}, onUpdateClick: () -> Unit = {}
) {
    val uiState = webClient.uiState

    LaunchedEffect(null) {
        webClient.getConteudo()
    }

    Conteudo(LocalContext.current, urlApi, uiState, onSearchClick, onUpdateClick)

}


@Composable
fun Conteudo(
    context: Context,
    urlApi: String,
    uiState: ConteudoUiState,
    onSearchClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {}
) {

    LazyColumn {
        item {
            ConteudoHeader(urlApi, onSearchClick = onSearchClick, onUpdateClick = onUpdateClick)
        }

        item {
            if (uiState.conteudos.isNotEmpty()) {
                Text(
                    text = "Conteudos",
                    modifier = Modifier.padding(start = 18.dp, top = 32.dp, bottom = 4.dp),
                    fontSize = 20.sp,
                    color = Color.Gray,
                )
            }
        }

        items(uiState.conteudos) { it ->
            RepositoryItem(conteudo = it) { conteudo ->

                val intent = Intent(context, ConteudoActivity::class.java)
                val gson = Gson()
                val conteudoEmJson = gson.toJson(conteudo)
                intent.putExtra("conteudoEmJson", conteudoEmJson)
                intent.putExtra("URL_API", urlApi)


                startActivity(context, intent, null)
            }
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ConteudoHeader(
    apiName: String,
    onSearchClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {}
) {
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
                    Color(0xFFFDD835),
                )
                .height(boxHeight)

        ) {
            Text(
                text = "Imersão Java",
                color = Color.DarkGray,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.Center)

            )

            Row(
                modifier = Modifier
                    .offset(y = imageHeight / 2)
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 4.dp)
            ) {
                Chip(
                    onClick = { onUpdateClick() },
                    border = ChipDefaults.outlinedBorder,
                    colors = ChipDefaults.outlinedChipColors(),
                    modifier = Modifier
                        .padding(end = 4.dp)

                ) {
                    Text(
                        text = "Atualizar",
                    )
                }

                Chip(
                    onClick = { onSearchClick() },
                    border = ChipDefaults.outlinedBorder,
                    colors = ChipDefaults.outlinedChipColors(),

                    ) {
                    Text(
                        text = "Mudar API",
                    )
                }
            }

        }


        Column(
            Modifier
                .padding(18.dp, top = 16.dp)
                .fillMaxWidth(),

            ) {
            Text("API", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                text = apiName,
                modifier = Modifier.padding(top = 12.dp),
                fontSize = 16.sp,
                color = Color.Gray,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RepositoryItem(
    conteudo: ConteudoRepository,
    onItemClick: (conteudo: ConteudoRepository) -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(start = 18.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        elevation = 4.dp,
        onClick = {
            onItemClick(conteudo);
        }
    ) {
        Row(
        ) {
            val boxHeight = remember { 120.dp }

            val imageHeight = remember { boxHeight }

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
                    "Votos: ${conteudo.ranking}",
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
        LocalContext.current,
        "https://sticker-doxo-api.herokuapp.com/linguagens",
        uiState = ConteudoUiState(
            id = "id",
            user = "https://sticker-doxo-api.herokuapp.com/linguagens",
            image = "https://avatars.githubusercontent.com/u/35709152?v=4",
            name = "Junior",
            conteudos = listOf(
                ConteudoRepository(
                    id = "id",
                    title = "Conteudo for first test",
                    image = "Preview description 1"
                ),
                ConteudoRepository(
                    id = "id",
                    title = "Repository for second test",
                    image = "Preview description 2"
                )
            )
        )
    )

}

data class ConteudoUiState(
    val id: String = "",
    val user: String = "",
    val image: String = "",
    val name: String = "",
    val ranking: String = "",
    val conteudos: List<ConteudoRepository> = emptyList()
)
