package com.calorify.app.ui.component.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Shapes

@Composable
fun Chip(text: String) {
    Surface(
        color = Blue500,
        shape = Shapes.medium,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChipPreview() {
    CalorifyTheme {
        Chip("Hemat 50%")
    }
}