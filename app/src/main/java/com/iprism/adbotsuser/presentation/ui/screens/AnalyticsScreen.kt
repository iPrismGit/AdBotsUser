package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import com.iprism.adbotsuser.presentation.ui.theme.MontserratFamily
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.ui.components.LoadingScreen
import com.iprism.adbotsuser.presentation.ui.theme.BLACK
import com.iprism.adbotsuser.presentation.ui.theme.DarkBlue
import com.iprism.adbotsuser.presentation.ui.theme.DarkRed
import com.iprism.adbotsuser.presentation.ui.theme.Green
import com.iprism.adbotsuser.presentation.ui.theme.Red
import com.iprism.adbotsuser.presentation.ui.theme.Red1
import com.iprism.adbotsuser.presentation.viewmodels.HomeViewModel
import com.iprism.adbotsuser.utils.UiState

@Composable
fun AnalyticsScreen(onNavWalletHistory:() -> Unit, onNavPromotionDetails :(String) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {

    val promotions by viewModel.promotions.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsStateWithLifecycle()
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
        Column {
            TotalEarningsSection()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Promotions",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkRed,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(horizontal = 12.dp)) {
                itemsIndexed(promotions) { index, item ->
                    if (index >= promotions.size - 1) {
                        viewModel.fetchPromotions()
                    }
                    PromotionCardInAnalytics(item, {onNavPromotionDetails(item.id)})
                }
                if (isPaginationLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }
    if (uiState is UiState.Loading && promotions.isEmpty()) {
        LoadingScreen()
    }

    if (uiState is UiState.Error && promotions.isEmpty()) {
        Text(
            text = (uiState as UiState.Error).message,
            color = BLACK
        )
    }
}

@Composable
fun TotalEarningsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F5F9))
            .padding(12.dp)
    ) {
        Text(
            text = "Total Earnings",
            color = Red1,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MontserratFamily
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.coin_img),
                contentDescription = "Earnings",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "₹800",
                    color = Green,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MontserratFamily
                )
                Text(
                    text = "Total Earned Amount",
                    color = Green,
                    fontSize = 16.sp,
                    fontFamily = MontserratFamily
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Min withdraw Amount ₹1000 *",
                    color = Red,
                    fontSize = 14.sp,
                    fontFamily = MontserratFamily
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { /* Handle Redeem */ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(
                text = "Redeem now",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
@Preview
fun AnalyticsScreenPreview() {
    AnalyticsScreen({}, {})
}