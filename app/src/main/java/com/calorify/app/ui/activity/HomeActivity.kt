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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.calorify.app.ui.component.BottomBar
import com.calorify.app.ui.navigation.Screen
import com.calorify.app.ui.screen.HistoryLogScreen
import com.calorify.app.ui.screen.HomeScreen
import com.calorify.app.ui.screen.ProfileScreen
import com.calorify.app.ui.screen.ScanCalorieScreen
import com.calorify.app.ui.theme.CalorifyTheme
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

        Scaffold(
//            topBar = {
//                if (currentRoute == Screen.DetailRecipe.route) {
//
//                    TopBar(
//                        onBackClick = {navController.navigateUp()}
//                    )
//                }
//            },
            bottomBar = {
//                if (currentRoute != Screen.DetailRecipe.route) {
//                    BottomBar(navController)
//                }
                BottomBar(navController)
            },
            modifier = modifier
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.History.route) {
                    HistoryLogScreen(navigateToDetail = {})
                }
                composable(Screen.Scan.route) {
                    ScanCalorieScreen()
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
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

