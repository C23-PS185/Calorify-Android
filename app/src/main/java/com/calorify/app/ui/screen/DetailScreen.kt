package com.calorify.app.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.data.remote.response.LogItem
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Neutral500
import com.calorify.app.ui.theme.Neutral700
import com.calorify.app.viewmodel.ListLogViewModel

@Composable
fun DetailScreen(
    logId: String,
    viewModel: ListLogViewModel,
) {
    Log.d("LOG ID", "DetailScreen: $logId")
    val logItemState = remember { mutableStateOf<LogItem?>(null) }

    LaunchedEffect(logId) {
        val logItem = viewModel.getLogItemById(logId)
        logItemState.value = logItem
    }

    val logKalori = logItemState.value

    if (logKalori != null) {
        Log.d("LOG KALORI", "DetailScreen: ${logKalori.foodName}")
    }

    if (logKalori != null) {
        DetailContent(
            logKalori.foodName!!,
            "https://firebasestorage.googleapis.com/v0/b/calorify-app.appspot.com/o/food_images%2Fapple%20pie.jpg?alt=media&token=b88fd136-bae7-4521-87b1-3434c5ffc567&_gl=1*10llus1*_ga*NzU5NDgzNTY3LjE2ODQ1MTE3Mjk.*_ga_CW55HF8NVT*MTY4NjAzNzkwNy4xNi4xLjE2ODYwNDAxMTQuMC4wLjA.",
            logKalori.fnbType!!,
            logKalori.eatTime!!,
            logKalori.foodCalories!!,
            logKalori.createdAtDate!!,
            logKalori.createdAtTime!!,
        )
    } else {
        LoadingScreen()
    }
}

@Composable
fun DetailContent(
    title: String,
    photoUrl: String,
    category: String,
    eatTime: String,
    totalCalories: Int,
    createdAtDate: String,
    createdAtTime: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(240.dp)
                    .padding(bottom = 0.dp, start = 16.dp, end = 16.dp, top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(vertical = 16.dp),
            ) {
                item {
                    TextGroup(title = "Nama", content = title)
                    TextGroup(title = "Kategori", content = category)
                    TextGroup(title = "Waktu Makan", content = eatTime)
                    TextGroup(title = "Total Kalori", content = "$totalCalories kal")
                    TextGroup(title = "Dibuat Pada", content = "$createdAtDate, $createdAtTime")
                }
            }
        }
    }
}

@Composable
fun TextGroup(
    title: String,
    content: String,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.padding(vertical = 8.dp)){
        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            color = Neutral500,
        )
        Text(
            text = content,
            fontSize = 20.sp,
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            color = Neutral700,
        )
    }
}