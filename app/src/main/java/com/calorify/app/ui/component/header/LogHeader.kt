package com.calorify.app.ui.component.header

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Orange500

@Composable
fun LogHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Orange500,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = FontFamily(
                Font(resId = R.font.inter_semibold),
            ),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogHeaderPreview() {
    CalorifyTheme {
        LogHeader(title = "Sarapan")
    }
}