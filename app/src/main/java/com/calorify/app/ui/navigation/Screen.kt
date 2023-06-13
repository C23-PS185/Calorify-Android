package com.calorify.app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Scan : Screen("scan")
    object ScanResult : Screen("scanResult")
    object ScanLogResult : Screen("scanLogResult")
    object History : Screen("history")
    object Profile : Screen("profile")
    object MyProfile : Screen("myProfile")
    object EditProfile : Screen("editProfile")
    object ChangePassword : Screen("changePassword")
    object SelfAssessment : Screen("selfAssessment")
    object SelfAssessmentResult : Screen("selfAssessmentResult")
    object PremiumSubscription : Screen("premiumSubscription")
    object DetailLog : Screen("home/{logId}") {
        fun createRoute(logId: String) = "home/$logId"
    }
}