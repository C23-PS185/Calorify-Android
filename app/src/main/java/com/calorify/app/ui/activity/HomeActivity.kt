package com.calorify.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.component.bar.BottomBar
import com.calorify.app.ui.component.bar.TopBar
import com.calorify.app.ui.navigation.Screen
import com.calorify.app.ui.screen.DetailScreen
import com.calorify.app.ui.screen.HistoryLogScreen
import com.calorify.app.ui.screen.HomeScreen
import com.calorify.app.ui.screen.ScanCalorieScreen
import com.calorify.app.ui.screen.profile.ChangePasswordScreen
import com.calorify.app.ui.screen.profile.EditProfileScreen
import com.calorify.app.ui.screen.profile.MyProfileScreen
import com.calorify.app.ui.screen.profile.PremiumSubscriptionScreen
import com.calorify.app.ui.screen.profile.ProfileScreen
import com.calorify.app.ui.screen.profile.SelfAssessmentResultScreen
import com.calorify.app.ui.screen.profile.SelfAssessmentScreen
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.viewmodel.ViewModelFactory2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            CalorifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CalorifyNavigation()
                }
            }
        }
    }

    @Composable
    fun CalorifyNavigation(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val childPage = listOf(Screen.DetailLog.route, Screen.MyProfile.route, Screen.EditProfile.route, Screen.ChangePassword.route, Screen.SelfAssessment.route, Screen.SelfAssessmentResult.route, Screen.PremiumSubscription.route)
        Scaffold(
            topBar = {
                if (currentRoute in childPage) {
                    TopBar(
                        onBackClick = {navController.navigateUp()}
                    )
                }
            },
            bottomBar = {
                if (currentRoute !in childPage) {
                    BottomBar(navController)
                }
            },
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen( navigateToDetail = { logId ->
                        navController.navigate(Screen.DetailLog.createRoute(logId))
                    })
                }
                composable(Screen.History.route) {
                    HistoryLogScreen( navigateToDetail = { logId ->
                        navController.navigate(Screen.DetailLog.createRoute(logId))
                    })
                }
                composable(Screen.Scan.route) {
                    ScanCalorieScreen()
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        name = "Melati",
                        photoUrl = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY",
                        email = "melati@gmail.com",
                        onMyProfileClick = { navController.navigate(Screen.MyProfile.route)},
                        onChangePasswordClick = { navController.navigate(Screen.ChangePassword.route)},
                        onSelfAssessmentResultClick = { navController.navigate(Screen.SelfAssessmentResult.route)},
                        onPremiumSubscriptionClick = { navController.navigate(Screen.PremiumSubscription.route)},
                        onSignOut = { signOut() })
                }
                composable(
                    route = Screen.DetailLog.route,
                    arguments = listOf(navArgument("logId") { type = NavType.IntType }),
                ) {
                    val id = it.arguments?.getInt("logId") ?: 0
                    DetailScreen(
                        viewModel = viewModel(factory = ViewModelFactory2(LogRepository(
                            LocalContext.current), id)
                        )
                    )
                }
                composable(Screen.MyProfile.route) {
                    MyProfileScreen(
                        name = "Melati Eka Putri",
                        photoUrl = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY",
                        email = "melati@gmail.com",
                        birthDate = "25 Maret 2002",
                        age = 21,
                        gender = "Perempuan",
                        onButtonClick = { navController.navigate(Screen.EditProfile.route)}
                    )
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(
                        photoUrl = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY",
                        name = "Melati Eka Putri",
                        gender = "Perempuan",
                        birthDate = "25/03/2002"
                    )
                }
                composable(Screen.ChangePassword.route) {
                    ChangePasswordScreen()
                }
                composable(Screen.SelfAssessmentResult.route) {
                    SelfAssessmentResultScreen(onDoAssessmentClick = { navController.navigate(Screen.SelfAssessment.route)})
                }
                composable(Screen.SelfAssessment.route) {
                    SelfAssessmentScreen()
                }
                composable(Screen.PremiumSubscription.route) {
                    PremiumSubscriptionScreen()
                }
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

