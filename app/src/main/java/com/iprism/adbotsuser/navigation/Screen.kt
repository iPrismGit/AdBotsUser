package com.iprism.adbotsuser.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Analytics : Screen("analytics")
    object PromotionDetails : Screen("promotion_details/{id}") {
        fun createRoute(id: String) = "promotion_details/$id"
    }
    object ReportSuccess : Screen("report_success/{message}") {
        fun createRoute(message : String) = "report_success/$message"
    }
    object WalletHistory : Screen("wallet_history")
}