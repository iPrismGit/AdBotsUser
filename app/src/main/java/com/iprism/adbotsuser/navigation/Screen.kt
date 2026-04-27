package com.iprism.adbotsuser.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Analytics : Screen("analytics")
    object PromotionDetails : Screen("promotion_details/{id}") {
        fun createRoute(id: String) = "promotion_details/$id"
    }
}