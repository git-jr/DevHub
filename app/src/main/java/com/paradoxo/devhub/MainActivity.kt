package com.paradoxo.devhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.paradoxo.devhub.ui.theme.DevHubTheme
import com.paradoxo.devhub.ui.theme.GitHubWebClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DevHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    InfoCard("git-jr")
                }
            }
        }
    }
}

@Composable
fun InfoCard(user: String, webClient: GitHubWebClient = GitHubWebClient()) {

    val findedUser by webClient.getProfilebyUser(user)
        .collectAsState(initial = null)
    findedUser?.let {

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


                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = it.avatar_url)
                        .crossfade(true)
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

                Text(it.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(it.login, fontSize = 20.sp, color = Color.Gray)
                Text(
                    it.bio,
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DevHubTheme {
        InfoCard("git-jr")
    }
}