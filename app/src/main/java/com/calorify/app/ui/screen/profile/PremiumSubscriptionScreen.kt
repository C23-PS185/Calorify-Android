package com.calorify.app.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.calorify.app.R
import com.calorify.app.ui.component.button.CustomButton
import com.calorify.app.ui.component.card.SubscriptionCard
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Neutral500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PremiumSubscriptionScreen() {
    var selectedCard by remember { mutableStateOf(-1) }

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            ImageVector.vectorResource(id = R.drawable.doctor),
            contentDescription = stringResource(R.string.my_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(240.dp)
                .clip(CircleShape)
        )
        Text(
            text = "Berlangganan Premium",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_bold),
            ),
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Nikmati saran diet yang lebih personal dan fitur konsultasi dengan ahli gizi berlisensi",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_regular),
            ),
            textAlign = TextAlign.Center,
            color = Neutral500,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
        Row() {
            SubscriptionCard(
                discountValue = 50,
                numberOfMonth = 12,
                price = "Rp50.000,00",
                selectedCard = selectedCard,
                onCardSelected = { card ->
                    selectedCard = card
                })
            SubscriptionCard(
                discountValue = 25,
                numberOfMonth = 3,
                price = "Rp75.000,00",
                selectedCard = selectedCard,
                onCardSelected = { card ->
                    selectedCard = card
                })
            SubscriptionCard(
                numberOfMonth = 1,
                price = "Rp100.000,00",
                selectedCard = selectedCard,
                onCardSelected = { card ->
                    selectedCard = card
                })
        }
        CustomButton(icon = R.drawable.ic_star, text = "Berlangganan Sekarang", onClick = {
            CoroutineScope(Dispatchers.Main).launch {
                // Show the toast message
                Toast.makeText(
                    context,
                    "Tunggu fitur ini pada pengembangan selanjutnya",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PremiumSubscriptionScreenPreview() {
    CalorifyTheme {
        PremiumSubscriptionScreen()
    }
}