package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iprism.adbotsuser.presentation.ui.theme.DarkRed

@Composable
fun AnalyticsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Promotions",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkRed
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(8) {
                    PromotionCardInAnalytics({ navController.navigate("promotion_details") })
                }
            }
        }
    }
}