package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsuser.presentation.ui.theme.DarkBlue
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.data.models.promotions.PromotionsItem
import com.iprism.adbotsuser.presentation.ui.components.LoadingScreen
import com.iprism.adbotsuser.presentation.ui.components.LogoutDialog
import com.iprism.adbotsuser.presentation.ui.theme.BLACK
import com.iprism.adbotsuser.presentation.ui.theme.DarkRed
import com.iprism.adbotsuser.presentation.ui.theme.Green
import com.iprism.adbotsuser.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsuser.presentation.ui.theme.Red
import com.iprism.adbotsuser.presentation.ui.theme.White
import com.iprism.adbotsuser.presentation.viewmodels.HomeViewModel
import com.iprism.adbotsuser.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogout: () -> Unit, onNavPromotionDetails :(String) -> Unit, viewModel: HomeViewModel) {

    var showLogoutDialog by remember { mutableStateOf(false) }
    val promotions by viewModel.promotions.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsStateWithLifecycle()
    val userDetailsState by viewModel.userDetails.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                viewModel.logout {
                    onLogout()
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(DarkBlue)
                            .padding(12.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    GradientDivider()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    GradientDivider()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    GradientDivider()
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Image(
                                    painter = painterResource(R.drawable.add_bots_logo),
                                    contentDescription = "Location",
                                    modifier = Modifier.size(width = 120.dp, height = 60.dp),
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    GradientDivider()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    GradientDivider()
                                    Spacer(modifier = Modifier.height(8.dp))
                                    GradientDivider()
                                }
                            }
                            Column(
                                modifier = Modifier.align(Alignment.CenterEnd).padding(top = 20.dp, end = 16.dp)
                                    .clickable(onClick = { showLogoutDialog = true }),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.coin_img),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "Logout", color = White,
                                    fontFamily = MontserratFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.location_img1),
                                contentDescription = "Location",
                                modifier = Modifier.size(46.dp),
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = if (userDetailsState is UiState.Success) (userDetailsState as UiState.Success).data.response.location else "",
                                        color = White,
                                        fontFamily = MontserratFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                            val statusText =
                                if (userDetailsState is UiState.Success) (userDetailsState as UiState.Success).data.response.status else "Offline"
                            val isOnline = statusText.lowercase() == "online"
                            Text(
                                text = statusText.replaceFirstChar { it.uppercase() },
                                color = White,
                                fontFamily = MontserratFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .background(
                                        color = if (isOnline) Green else Red,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp)
                            )
                        }
                    }
                    GradientDivider()
                }

                item {
                    Text(
                        text = "Promotions",
                        style = MaterialTheme.typography.headlineSmall,
                        color = DarkRed,
                        modifier = Modifier.padding(horizontal = 12.dp).background(MaterialTheme.colorScheme.background)
                    )
                }

                itemsIndexed(promotions) { index, item ->
                    if (index >= promotions.size - 1) {
                        viewModel.fetchPromotions()
                    }
                    Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                        PromotionCardInAnalytics(item, { onNavPromotionDetails(item.id) })
                    }
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
        if (uiState is UiState.Loading && promotions.isEmpty()) {
            LoadingScreen()
        }

        if (uiState is UiState.Error && promotions.isEmpty()) {
            Text(
                text = (uiState as UiState.Error).message,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun PromotionCardInAnalytics(promotionsItem: PromotionsItem, onAnalyticsClick: () -> Unit) {
    val cardGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF015DC5), Color(0xFF559CEE))
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardGradient)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = promotionsItem.name,
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                /*Text(
                    text = " (${promotionsItem.bussinessName})",
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )*/
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start Date : ${promotionsItem.startDate}",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Created On: ${promotionsItem.createdOn}",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }

        Button(
            onClick = { onAnalyticsClick() },
            modifier = Modifier
                .height(32.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text(
                text = "View Analytics",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onError,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun GradientDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        White,
                        DarkRed,
                        White
                    )
                )
            )
    )
}