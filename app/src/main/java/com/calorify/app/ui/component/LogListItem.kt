package com.calorify.app.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun LogListItem(
    title: String,
    photoUrl: String,
    calorie: Int,
    time: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(72.dp)
                .clip(CircleShape)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_regular),
                ),

            )
            Text(
                text = "$calorie kal",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_bold),
                ),
                fontSize = 14.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = time,
                fontSize = 10.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_regular),
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogListItemPreview() {
    CalorifyTheme {
        LogListItem(
            title = "Nasi Goreng Ayam Kampung",
            photoUrl = "",
            calorie = 200,
            time = "07.23"
        )
    }
}