package com.calorify.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.repository.LogRepository
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Neutral500
import com.calorify.app.ui.theme.Neutral700
import com.calorify.app.viewmodel.DetailViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
) {
    val logKalori by viewModel.logKalori.collectAsState()
    DetailContent(
        logKalori.title,
        logKalori.photo,
        logKalori.category,
        logKalori.eatTime,
        logKalori.portion,
        logKalori.totalCalories,
        logKalori.createdAtDate,
        logKalori.createdAtTime,
    )
}

@Composable
fun DetailContent(
    title: String,
    photoUrl: String,
    category: String,
    eatTime: String,
    portion: Int,
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
                    TextGroup(title = "Banyak Porsi", content = portion.toString())
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

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    val logKalori = LogRepository(LocalContext.current).listLogKalori[0]
    CalorifyTheme {
        DetailContent(
            logKalori.title,
            logKalori.photo,
            logKalori.category,
            logKalori.eatTime,
            logKalori.portion,
            logKalori.totalCalories,
            logKalori.createdAtDate,
            logKalori.createdAtTime,
        )
    }
}