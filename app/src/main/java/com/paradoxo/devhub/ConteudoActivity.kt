package com.paradoxo.devhub

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.paradoxo.devhub.model.ConteudoRepository
import com.paradoxo.devhub.ui.theme.DevHubTheme
import com.paradoxo.devhub.webclient.ConteudoWebClient

class ConteudoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            DevHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val intent = (this).intent

                    val webClient = ConteudoWebClient(intent.getStringExtra("URL_API").toString())


                    val conteudoEmJson = intent.getStringExtra("conteudoEmJson")
                    val gson = Gson()
                    val conteudo = gson.fromJson(conteudoEmJson, ConteudoRepository::class.java)


                    PorfileHeader(conteudo, onVoteClick = { id ->
                        webClient.votar(id)
                        Log.i("onVoteClick", id)
                        Toast.makeText(this, "Você votou em ${conteudo.title}", Toast.LENGTH_SHORT)
                            .show()


                    }, onAddStickerClick = { id ->
                        Toast.makeText(this, "Ainda não disponível", Toast.LENGTH_SHORT).show()
                    }
                    )
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PorfileHeader(
    conteudo: ConteudoRepository,
    onVoteClick: (id: String) -> Unit = {},
    onAddStickerClick: (id: String) -> Unit = {}
) {
    Column() {
        val boxHeight = remember { 350.dp }
        val imageHeight = remember { boxHeight }

        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF128C7E))
                    .height(boxHeight)

            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(conteudo.image)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.profile_placeholder),
                    contentDescription = "Image do conteudo",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(300.dp)

                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 100.dp)
                .align(Alignment.CenterHorizontally)

        ) {

            Text(
                text = conteudo.title,
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Votos: ${conteudo.ranking}",
                fontSize = 18.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { onVoteClick(conteudo.id) }
                ,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFDD835),
                    contentColor = Color.DarkGray
                ),

                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(80.dp)

            ) {
                Text(
                    text = "VOTAR",
                    color = Color.DarkGray,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                onClick = { onAddStickerClick(conteudo.id) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF128C7E),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Text(
                    text = "ADICIONAR AO WHATSAPP",
                    color = Color.White,
                )
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun PorfileHeaderPreview() {
    ConteudoRepository(
        "1",
        "Python",
        image = "https://avatars.githubusercontent.com/u/35709152?v=4",
        ""
    )
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Composable
fun DefaultPreview() {
    DevHubTheme {
        Greeting("Android")
    }
}