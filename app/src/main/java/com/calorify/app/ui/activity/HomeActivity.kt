package com.calorify.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.component.CustomButton
import com.calorify.app.ui.component.HomeHeader
import com.calorify.app.ui.component.ListLog
import com.calorify.app.ui.component.LogHeader
import com.calorify.app.ui.component.LogListItem
import com.calorify.app.ui.component.PieChart
import com.calorify.app.ui.component.ScrollToTopButton
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.viewmodel.ListLogViewModel
import com.calorify.app.viewmodel.ViewModelFactory
import com.calorify.app.viewmodel.ViewModelFactory2
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column () {
                        HomeHeader(name = "Melati", photo = "https://media.licdn.com/dms/image/C4E03AQHzTBTfofQsig/profile-displayphoto-shrink_800_800/0/1616565306427?e=1690416000&v=beta&t=z7qPZl4pHH1o5220VLLO0ZofQ2Nj4W-dYBY2vyADeBY")
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ){
                            PieChart()
                            CustomButton(R.drawable.ic_add_circle_outline_24, text = "Tambahkan Log Kalori", onClick = {})
                            ListLog(navigateToDetail = {})
                        }
                    }
                }
            }
        }
    }
}

