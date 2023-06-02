package com.calorify.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.ui.screen.ProfileRow
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Shapes
import com.calorify.app.ui.theme.White

@Composable
fun SubscriptionCard(
    discountValue: Int = 0,
    numberOfMonth: Int,
    price: String,
){
    Surface(
        color = White,
        shape = Shapes.medium,
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = numberOfMonth.toString(),
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_bold),
                ),
            )
            Text(
                text = "months",
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_regular),
                ),
            )
            Text(
                text = price,
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_semibold),
                ),
            )
            Text(
                text = "/Bulan",
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_semibold),
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCardPreview() {
    CalorifyTheme {
        SubscriptionCard(50, 12, "Rp50.000,00")
    }
}