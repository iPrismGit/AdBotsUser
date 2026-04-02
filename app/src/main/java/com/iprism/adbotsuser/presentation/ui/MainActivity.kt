package com.iprism.adbotsuser.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iprism.adbotsuser.presentation.ui.theme.AdBotsUserTheme
import com.iprism.adbotsuser.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdBotsUserTheme {
                AppNavHost()
            }
        }
    }
}