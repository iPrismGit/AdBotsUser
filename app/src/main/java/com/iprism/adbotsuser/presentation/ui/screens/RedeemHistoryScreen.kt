package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.data.models.wallethistory.HistoryItem
import com.iprism.adbotsuser.presentation.ui.components.LoadingScreen
import com.iprism.adbotsuser.presentation.ui.theme.BLACK
import com.iprism.adbotsuser.presentation.ui.theme.LightGrey2
import com.iprism.adbotsuser.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsuser.presentation.viewmodels.RedeemHistoryViewModel
import com.iprism.adbotsuser.utils.UiState

@Composable
fun RedeemHistoryScreen(
    navController: NavHostController,
    viewModel: RedeemHistoryViewModel = hiltViewModel()
) {
    val historyItems by viewModel.historyItems.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_img),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Redeem History",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(all = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(historyItems) { index, transaction ->
                    if (index >= historyItems.size - 1) {
                        viewModel.fetchWalletHistory()
                    }
                    TransactionItem(transaction)
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

        if (uiState is UiState.Loading && historyItems.isEmpty()) {
            LoadingScreen()
        }

        if (uiState is UiState.Error && historyItems.isEmpty()) {
            Text(
                text = (uiState as UiState.Error).message,
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun TransactionItem(transaction: HistoryItem) {
    val amountColor = if (transaction.type.lowercase() == "debit") Color.Red else Color(0xFF00C566)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.transactionType,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "₹${transaction.amount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = amountColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.description,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
            Text(
                text = transaction.createdOn,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
        }
    }
}