package com.calorify.app.ui.screen


import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.calorify.app.R
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.component.CustomButton
import com.calorify.app.ui.component.HomeHeader
import com.calorify.app.ui.component.LineGraph
import com.calorify.app.ui.component.ListLog
import com.calorify.app.ui.component.MonthSelection
import com.calorify.app.ui.component.PieChart
import com.calorify.app.ui.component.SearchBar
import com.calorify.app.viewmodel.ListLogViewModel
import com.calorify.app.viewmodel.ViewModelFactory2

@Composable
fun HistoryLogScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
){
    Column () {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding()
        ){
            ListLog("date", navigateToDetail = {})
        }
    }
}