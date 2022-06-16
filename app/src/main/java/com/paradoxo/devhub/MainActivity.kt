package com.paradoxo.devhub

import android.os.Bundle
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
import com.paradoxo.devhub.screen.AuthenticationScreen
import com.paradoxo.devhub.screen.InfoCardScreen
import com.paradoxo.devhub.ui.theme.DevHubTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevHubTheme {
                var screenState: AppsScreens by remember {
                    mutableStateOf(AppsScreens.Authentication)
                }

                var user by remember {
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
                                    if (it.isNotBlank()) {
                                        user = it
                                        screenState = AppsScreens.Profile
                                    }
                                }
                            )
                        }
                        AppsScreens.Profile -> {
                            InfoCardScreen(user = user, onSearchClick = {
                                val intent = intent
                                finish()
                                startActivity(intent)
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
    object Profile : AppsScreens()
}

