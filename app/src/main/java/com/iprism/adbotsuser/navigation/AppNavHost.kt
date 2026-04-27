package com.iprism.adbotsuser.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iprism.adbotsuser.presentation.ui.components.BottomNavigationBar
import com.iprism.adbotsuser.presentation.ui.screens.LoginScreen
import com.iprism.adbotsuser.presentation.ui.screens.SplashScreen
import com.iprism.adbotsuser.presentation.ui.screens.AnalyticsScreen
import com.iprism.adbotsuser.presentation.ui.screens.HomeScreen

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
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    })
            }
            composable(Screen.Login.route) {
                LoginScreen({
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                })
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Analytics.route) {
                AnalyticsScreen(navController)
            }
        }
    }
}
