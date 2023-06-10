package com.calorify.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.calorify.app.R
import com.calorify.app.data.remote.response.DataUser
import com.calorify.app.helper.NetworkManager
import com.calorify.app.helper.Result
import com.calorify.app.ui.component.bar.BottomBar
import com.calorify.app.ui.component.bar.TopBar
import com.calorify.app.ui.navigation.Screen
import com.calorify.app.ui.screen.DetailScreen
import com.calorify.app.ui.screen.HistoryLogScreen
import com.calorify.app.ui.screen.HomeScreen
import com.calorify.app.ui.screen.LoadingScreen
import com.calorify.app.ui.screen.ScanCalorieScreen
import com.calorify.app.ui.screen.profile.ChangePasswordScreen
import com.calorify.app.ui.screen.profile.EditProfileScreen
import com.calorify.app.ui.screen.profile.MyProfileScreen
import com.calorify.app.ui.screen.profile.PremiumSubscriptionScreen
import com.calorify.app.ui.screen.profile.ProfileScreen
import com.calorify.app.ui.screen.profile.SelfAssessmentResultScreen
import com.calorify.app.ui.screen.profile.SelfAssessmentScreen
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.viewmodel.AssessmentResultViewModel
import com.calorify.app.viewmodel.ListLogViewModel
import com.calorify.app.viewmodel.ViewModelFactory
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


class HomeActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var userId: String
    private lateinit var userData: DataUser

    private val assessmentResultViewModel by viewModels<AssessmentResultViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private val listLogViewModel by viewModels<ListLogViewModel> {
        ViewModelFactory.getInstance(application)
    }

    val dateMonthYear = formatDate(LocalDate.now())
    val date = dateMonthYear.subSequence(0,2).toString()
    val monthYear = dateMonthYear.subSequence(3, dateMonthYear.length).toString()
    val month = monthYear.substring(0,2)
    val year = monthYear.substring(3,7)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(NetworkManager.isConnectedToNetwork(this)){
            auth = Firebase.auth
            currentUser = auth.currentUser!!
            userId = currentUser.uid
            listLogViewModel.fetchMonthlyData(true, this, userId, month=month, year=year, date=date)
            addUserData()
        } else {
            val i = Intent(this, NoConnectionActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }

    private fun addUserData(){
        assessmentResultViewModel.getUserResult(userId).observe(this@HomeActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        setContent {
                            CalorifyTheme {
                                LoadingScreen()
                            }
                        }
                    }

                    is Result.Success -> {
                        userData = result.data.data
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

                    is Result.Error -> {
                        if (result.error == "Data not found") {
                            startActivity(Intent(this, AssessmentActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Pengambilan data gagal. Harap cek koneksimu!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
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
                    HomeScreen(
                        firstName = userData.fullName!!.substringBefore(" "),
                        listLogViewModel = listLogViewModel,
                        calorieNeeded = userData.userCalorieIntake!!,
                        navigateToDetail = { logId ->
                        navController.navigate(Screen.DetailLog.createRoute(logId))
                    })
                }
                composable(Screen.History.route) {
                    HistoryLogScreen( month = month, listLogViewModel = listLogViewModel, navigateToDetail = { logId ->
                        navController.navigate(Screen.DetailLog.createRoute(logId))
                    })
                }
                composable(Screen.Scan.route) {
                    ScanCalorieScreen()
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        name = userData.fullName!!,
                        photoUrl = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY",
                        email = currentUser.email!!,
                        onMyProfileClick = { navController.navigate(Screen.MyProfile.route)},
                        onChangePasswordClick = { navController.navigate(Screen.ChangePassword.route)},
                        onSelfAssessmentResultClick = { navController.navigate(Screen.SelfAssessmentResult.route)},
                        onPremiumSubscriptionClick = { navController.navigate(Screen.PremiumSubscription.route)},
                        onSignOut = { signOut() })
                }
                composable(
                    route = Screen.DetailLog.route,
                    arguments = listOf(navArgument("logId") { type = NavType.StringType }),
                ) {
                    val id = it.arguments?.getString("logId") ?: ""
                    DetailScreen(
                        logId = id,
                        viewModel = listLogViewModel,
                    )
                }
                composable(Screen.MyProfile.route) {
                    MyProfileScreen(
                        name = userData.fullName!!,
                        photoUrl = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY",
                        email = currentUser.email!!,
                        birthDate = userData.birthDate!!,
                        age = calculateAge(userData.birthDate!!),
                        gender = userData.gender!!,
                        onButtonClick = { navController.navigate(Screen.EditProfile.route)}
                    )
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(
                        photoUrl = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY",
                        name = userData.fullName!!,
                        gender = userData.gender!!,
                        birthDate = userData.birthDate!!
                    )
                }
                composable(Screen.ChangePassword.route) {
                    ChangePasswordScreen { newPass, oldPass ->
                        changePassword(newPass, oldPass)
                    }
                }
                composable(Screen.SelfAssessmentResult.route) {
                    SelfAssessmentResultScreen(
                        weight = userData.userWeight!!,
                        height = userData.userHeight!!,
                        calorie = userData.userCalorieIntake!!,
                        weightGoal = convertStatusKesehatan(userData.weightGoal!!),
                        indexBmi = userData.userBMI!!,
                        onDoAssessmentClick = { navController.navigate(Screen.SelfAssessment.route)}
                    )
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

    private fun calculateAge(birthDate: String): Int {
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateOfBirth = format.parse(birthDate)

        val today = Calendar.getInstance().time
        val diffInMillis = today.time - dateOfBirth.time

        val ageInMillis = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        val age = (ageInMillis / 365).toInt()

        return age
    }

    private fun convertStatusKesehatan(id: Int) : String {
        val statusKesehatanValue = resources.getStringArray(R.array.tujuan_kesehatan)
        return statusKesehatanValue[id]
    }

    private fun formatDate(date: LocalDate): String {

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return date.format(formatter)
    }

    private fun changePassword(newPass: String, oldPass: String) : String {
        return try {
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, oldPass)
            currentUser.reauthenticate(credential)
            Log.d("PASSWORD", "changePassword: $newPass")
            Log.d("USER", "changePassword: $currentUser")
            currentUser.updatePassword(newPass)
            "Ubah Kata Sandi Berhasil"
        } catch (err: Error){
            "Ubah Kata Sandi Gagal"
        }
    }
}

