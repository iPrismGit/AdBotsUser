package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.ui.theme.DarkBlue
import com.iprism.adbotsuser.presentation.ui.theme.DarkRed

@Composable
fun AnalyticsScreen(onNavWalletHistory:() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkBlue)
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Analytics",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            IconButton(onClick = { onNavWalletHistory() }) {
                Icon(
                    painter = painterResource(R.drawable.history_img),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Promotions",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkRed
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(8) {
                    //PromotionCardInAnalytics({ navController.navigate("promotion_details") })
                }
            }
        }
    }
}