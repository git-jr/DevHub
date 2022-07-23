package com.paradoxo.devhub

import android.R
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.paradoxo.devhub.screen.AuthenticationScreen
import com.paradoxo.devhub.screen.InfoCardScreenConteudo
import com.paradoxo.devhub.ui.theme.DevHubTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DevHubTheme {
                var screenState: AppsScreens by remember {
                    mutableStateOf(AppsScreens.Authentication)
                }

                var apiName by remember {
                    mutableStateOf("")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    when (screenState) {
                        AppsScreens.Authentication -> {
                            AuthenticationScreen(
                                onEnterClick = {
                                    if (it.isBlank()) {
                                        apiName =
                                            "https://sticker-doxo-api.herokuapp.com/linguagens"
                                    } else {
                                        apiName = it
                                    }

                                    screenState = AppsScreens.Conteudo
                                }
                            )
                        }
                        AppsScreens.Conteudo -> {
                            InfoCardScreenConteudo(apiName = apiName, onSearchClick = {
                                val intent = intent
                                //finish()
                                //startActivity(intent)
                                adicionarFigurinha(this)
                            })
                        }
                    }

                }
            }
        }
    }
}

sealed class AppsScreens {
    object Authentication : AppsScreens()
    object Conteudo : AppsScreens()
}

fun adicionarFigurinha(context: Context) {

    val CONTENT_PROVIDER_AUTHORITY = "sticker_pack_authority"

    val intent = Intent()
    intent.action = "com.whatsapp.intent.action.ENABLE_STICKER_PACK"
    intent.putExtra("sticker_pack_id", R.attr.identifier)
//identifier is the pack's identifier in contents.json file

    intent.putExtra("sticker_pack_authority", CONTENT_PROVIDER_AUTHORITY)
//authority is the ContentProvider's authority. In the case of the sample app it is BuildConfig.CONTENT_PROVIDER_AUTHORITY.

    intent.putExtra("sticker_pack_name", "stickerPackName")
//stickerPackName is the name of the sticker pack.

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.i("Erro ao carregar imagem", "");
    }
}


