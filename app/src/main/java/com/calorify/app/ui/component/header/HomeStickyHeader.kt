package com.calorify.app.ui.component.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun HomeStickyHeader(
    name: String,
    photo: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Blue500,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Halo $name,",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(
                        Font(resId = R.font.inter_bold),
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Left,
                )
                Text(
                    text = "Bagaimana asupan kalorimu hari ini?",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(resId = R.font.inter_regular),
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Left,
                )
            }
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeStickyHeaderPreview() {
    CalorifyTheme {
        HomeStickyHeader(name = "John", photo = "")
    }
}