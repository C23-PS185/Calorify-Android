package com.calorify.app.ui.component.card

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.ui.component.chip.Chip
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Shapes
import com.calorify.app.ui.theme.White

@Composable
fun SubscriptionCard(
    discountValue: Int = 0,
    numberOfMonth: Int,
    price: String,
    selectedCard: Int,
    onCardSelected: (Int) -> Unit
){
    val isSelected = selectedCard == numberOfMonth

    val borderColor = if (isSelected) Blue500 else Color.Transparent

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onCardSelected(numberOfMonth) }
    ){
        Box(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 8.dp, start = 8.dp, end=8.dp)
                .border(1.dp, borderColor, shape = Shapes.medium)
        ) {
            Surface(
                color = White,
                shape = Shapes.medium,
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
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
        if (discountValue > 0) {
            Chip(text = "Hemat $discountValue%")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCardPreview() {
    CalorifyTheme {
        SubscriptionCard(50, 12, "Rp50.000,00", 50, {})
    }
}