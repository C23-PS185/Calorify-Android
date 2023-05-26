package com.calorify.app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Scan : Screen("scan")
    object History : Screen("history")
    object Profile : Screen("profile")
    object DetailLog : Screen("home/{logId}") {
        fun createRoute(logId: Int) = "home/$logId"
    }
}