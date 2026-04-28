package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.ui.components.LoadingScreen
import com.iprism.adbotsuser.presentation.ui.theme.BLACK
import com.iprism.adbotsuser.presentation.ui.theme.DarkBlue
import com.iprism.adbotsuser.presentation.ui.theme.DarkRed
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
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Promotions",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkRed
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
@Preview
fun AnalyticsScreenPreview() {
    AnalyticsScreen({}, {})
}