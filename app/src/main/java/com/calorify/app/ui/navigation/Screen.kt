package com.calorify.app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Scan : Screen("scan")
    object History : Screen("history")
    object Profile : Screen("profile")

    object MyProfile : Screen("myProfile")
    object ChangePassword : Screen("changePassword")
    object SelfAssessment : Screen("selfAssessment")
    object PremiumSubscription : Screen("premiumSubscription")
    object DetailLog : Screen("home/{logId}") {
        fun createRoute(logId: Int) = "home/$logId"
    }
}