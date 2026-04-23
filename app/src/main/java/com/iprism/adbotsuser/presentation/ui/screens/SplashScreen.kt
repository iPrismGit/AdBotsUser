package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavLogin : () -> Unit, onNavHome : () -> Unit, viewModel: SplashViewModel = hiltViewModel()) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        delay(500)
        if (isLoggedIn == true) {
            onNavHome()
        } else {
            onNavLogin()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(180.dp)
        )
    }
}
