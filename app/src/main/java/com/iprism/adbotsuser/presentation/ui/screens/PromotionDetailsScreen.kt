package com.iprism.adbotsuser.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.ui.components.LoadingScreen
import com.iprism.adbotsuser.presentation.ui.theme.BLACK
import com.iprism.adbotsuser.presentation.ui.theme.BLACK1
import com.iprism.adbotsuser.presentation.ui.theme.Green
import com.iprism.adbotsuser.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsuser.presentation.ui.theme.Red
import com.iprism.adbotsuser.presentation.ui.theme.White
import com.iprism.adbotsuser.presentation.viewmodels.PromotionDetailsViewModel
import com.iprism.adbotsuser.utils.UiState

@Composable
fun PromotionDetailsScreen(
    onBack: () -> Unit,
    onReportSuccess: (String) -> Unit,
    viewModel: PromotionDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.response.collectAsStateWithLifecycle()
    val reportState by viewModel.reportResponse.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(reportState) {
        if (reportState is UiState.Success) {
            onReportSuccess("Report Sent Successfully")
        } else if (reportState is UiState.Error) {
            onReportSuccess("Report Sent Successfully")
            Toast.makeText(context, (reportState as UiState.Error).message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 12.dp)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { onBack() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_img),
                contentDescription = "Back",
                tint = BLACK,
                modifier = Modifier.size(28.dp),
            )
        }

        when (state) {
            is UiState.Success -> {
                val details = (state as UiState.Success).data.response
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = details.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MontserratFamily,
                            color = Black
                        )
                        Text(
                            text = " (${details.bussinessName})",
                            fontSize = 20.sp,
                            color = Black,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 12.dp)
                            .background(shape = RoundedCornerShape(12.dp), color = Green)
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "₹${details.earnedAmount}",
                            fontSize = 30.sp,
                            color = White,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Earned Amount",
                            fontSize = 20.sp,
                            color = White,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Text(
                        text = "Video Analytics",
                        fontSize = 16.sp,
                        color = BLACK1,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Data Rows
                    AnalyticsRow(label = "Total Days", value = "${details.totalDays} days")
                    HorizontalDivider(thickness = 1.dp, color = BLACK1)

                    AnalyticsRow(label = "Price Per Minute", value = "₹${details.pricePerMinute}")
                    HorizontalDivider(thickness = 1.dp, color = BLACK1)

                    AnalyticsRow(label = "Play Time", value = "${details.playTime} Minutes")
                    HorizontalDivider(thickness = 1.dp, color = BLACK1)

                    AnalyticsRow(label = "Screens", value = details.noOfScreens)
                    HorizontalDivider(thickness = 1.dp, color = BLACK1)
                }

                OutlinedButton(
                    onClick = { viewModel.userReport(details.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    enabled = reportState !is UiState.Loading,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Red),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Red)
                ) {
                    if (reportState is UiState.Loading) {
                        CircularProgressIndicator(
                            color = Red,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Report",
                            style = MaterialTheme.typography.labelMedium,
                            color = Red,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }

            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = (state as UiState.Error).message, color = Color.Red)
                }
            }

            else -> {}
        }
    }

    if (state is UiState.Loading) {
        LoadingScreen()
    }
}

@Composable
fun AnalyticsRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}
