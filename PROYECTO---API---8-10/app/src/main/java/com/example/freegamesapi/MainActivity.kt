package com.example.freegamesapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.freegamesapi.ui.home.HomeView
import com.example.freegamesapi.ui.login.LoginView
import com.example.freegamesapi.ui.theme.FreeGamesAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreeGamesAPITheme {
                var isLoggedIn by remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0F0F13)
                ) {
                    if (!isLoggedIn) {
                        LoginView(
                            onLoginSuccess = {
                                isLoggedIn = true
                            }
                        )
                    } else {
                        HomeView()
                    }
                }
            }
        }
    }
}
