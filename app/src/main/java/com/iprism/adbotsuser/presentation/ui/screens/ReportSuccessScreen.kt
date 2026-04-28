package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsuser.presentation.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun ReportSuccessScreen(onBack: () -> Unit, message : String) {
    LaunchedEffect(Unit) {
        delay(3000)
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.tick_img), contentDescription = "Tick")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            fontSize = 18.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}

@Composable
@Preview
fun ReportSuccessScreenPreview() {
    ReportSuccessScreen({}, "Report Sent Successfully")
}