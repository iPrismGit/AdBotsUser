package com.iprism.adbotsuser.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.iprism.adbotsuser.presentation.ui.components.BottomNavigationBar
import com.iprism.adbotsuser.presentation.ui.screens.LoginScreen
import com.iprism.adbotsuser.presentation.ui.screens.SplashScreen
import com.iprism.adbotsuser.presentation.ui.screens.AnalyticsScreen
import com.iprism.adbotsuser.presentation.ui.screens.HomeScreen
import com.iprism.adbotsuser.presentation.ui.screens.PromotionDetailsScreen
import com.iprism.adbotsuser.presentation.ui.screens.ReportSuccessScreen
import com.iprism.adbotsuser.presentation.ui.screens.RedeemHistoryScreen
import com.iprism.adbotsuser.presentation.viewmodels.AnalyticsViewModel
import com.iprism.adbotsuser.presentation.viewmodels.HomeViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Analytics.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    {
                        navController.navigate("main") {   // 🔥 IMPORTANT
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    })
            }
            composable(Screen.Login.route) {
                LoginScreen({
                    navController.navigate("main") {   // 🔥 IMPORTANT
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                })
            }


            navigation(
                route = "main",
                startDestination = Screen.Home.route
            ) {

                composable(Screen.Home.route) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("main")
                    }

                    val viewModel: HomeViewModel = hiltViewModel(parentEntry)

                    HomeScreen(
                        viewModel = viewModel,
                        onLogout = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onNavPromotionDetails = { id ->
                            navController.navigate(Screen.PromotionDetails.createRoute(id))
                        }
                    )
                }

                composable(Screen.Analytics.route) { backStackEntry ->

                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("main")
                    }

                    val analyticsViewModel: AnalyticsViewModel =
                        hiltViewModel(parentEntry)

                    AnalyticsScreen(
                        viewModel = analyticsViewModel,
                        onNavWalletHistory = {
                            navController.navigate(Screen.WalletHistory.route)
                        },
                        onNavPromotionDetails = { id ->
                            navController.navigate(Screen.PromotionDetails.createRoute(id))
                        },
                        onNavRedeemSuccess = { message ->
                            navController.navigate(Screen.ReportSuccess.createRoute(message))
                        }
                    )
                }
            }
            composable(Screen.PromotionDetails.route) {
                PromotionDetailsScreen(
                    onBack = { navController.popBackStack() },
                    onReportSuccess = { message -> navController.navigate(Screen.ReportSuccess.createRoute(message)) }
                )
            }
            composable(Screen.ReportSuccess.route) { navBackStackEntry ->
                val message = navBackStackEntry.arguments?.getString("message") ?: ""
                ReportSuccessScreen(onBack = {navController.popBackStack()}, message)
            }
            composable(Screen.WalletHistory.route) {
                RedeemHistoryScreen(navController)
            }
        }
    }
}
