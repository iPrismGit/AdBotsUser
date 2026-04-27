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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.iprism.adbotsuser.presentation.ui.theme.White
import com.iprism.adbotsuser.presentation.viewmodels.HomeViewModel
import com.iprism.adbotsuser.utils.UiState

@Composable
fun HomeScreen(onNavPromotionDetails :(String) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {

    var showLogoutDialog by remember { mutableStateOf(false) }
    val promotions by viewModel.promotions.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsStateWithLifecycle()

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                // Handle logout logic here
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
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
                Column(modifier = Modifier.align(Alignment.CenterEnd).padding(top = 20.dp, end = 16.dp).clickable(onClick = {showLogoutDialog = true }), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(R.drawable.logout_img),
                        contentDescription = "Logo",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Logout", color = White,
                        fontFamily = MontserratFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,)
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
                            text = "Hyderabad",
                            color = White,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Road No 4, Banjara Hills...",
                        color = White,
                        fontFamily = MontserratFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "Online",
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(color = Green, shape = RoundedCornerShape(4.dp))
                        .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp)
                )
            }
        }
        GradientDivider()

        // Promotions Section
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
fun PromotionCardInAnalytics(promotionsItem: PromotionsItem, onAnalyticsClick: () -> Unit) {
    val cardGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF015DC5), Color(0xFF559CEE))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardGradient)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = promotionsItem.name,
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

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
                text = "End Date : ${promotionsItem.endDate}",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }

        Button(
            onClick = { onAnalyticsClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(32.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkRed)
        ) {
            Text(
                text = "View Analytics",
                fontSize = 12.sp,
                color = Color.White,
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