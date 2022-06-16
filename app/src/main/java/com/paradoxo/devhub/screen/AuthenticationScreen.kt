package com.paradoxo.devhub.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.paradoxo.devhub.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthenticationScreen(
    onEnterClick: (user: String) -> Unit = {}
) {
    var user by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {


        val imageLoaderGif = ImageLoader.Builder(LocalContext.current)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = "https://gist.githubusercontent.com/ManulMax/2d20af60d709805c55fd784ca7cba4b9/raw/bcfeac7604f674ace63623106eb8bb8471d844a6/github.gif")
                .crossfade(true)
                .build(),
            imageLoader = imageLoaderGif,
            placeholder = painterResource(R.drawable.profile_placeholder),
            contentDescription = "Animação logo GitHub",
            modifier = Modifier
                .clip(CircleShape)
                .padding(4.dp)
        )

        Text(
            "DevHub",
            fontSize = 64.sp,
            color = Color.Gray,
            modifier = Modifier.paddingFromBaseline(bottom = 64.sp)
        )
        OutlinedTextField(value = user, onValueChange = {
            user = it
        }, modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), label = { (Text(text = "Buscar usuário")) })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onEnterClick(user) },
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Pesquisar", Modifier.padding(8.dp))
        }

    }


}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AuthenticationScreen()
}