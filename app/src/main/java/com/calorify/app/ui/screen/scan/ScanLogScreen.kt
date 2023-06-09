package com.calorify.app.ui.screen.scan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.ui.component.button.CustomButton2
import com.calorify.app.ui.component.input.MealTimeSelection
import com.calorify.app.ui.component.input.RadioButtonType
import com.calorify.app.ui.theme.Blue50
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.Blue700
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Neutral500
import com.calorify.app.ui.theme.White
import com.google.android.gms.common.SignInButton.ColorScheme

@Composable
fun ScanLogScreen(
    onBerandaClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())

    ) {
        Text(
            text = "Tambah Log Kalori",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_bold),
            ),
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 16.dp)
        )
        AsyncImage(
            model = photoBitmap,
            contentDescription = stringResource(R.string.my_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 48.dp, bottom = 48.dp)
                .height(300.dp)
        )
        Text(
            text = "Nama",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
        )
        TextField(
            value = "Nasi Goreng",
            onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(top = 8.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(
                    BorderStroke(1.dp, Neutral500),
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Text(
            text = "Jenis",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
        )
        RadioButtonType(Modifier.padding(bottom = 8.dp))
        Text(
            text = "Waktu Makan",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
        )
        MealTimeSelection(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
        Text(
            text = "Banyak Porsi",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
        )
        TextField(
            value = "1",
            onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(top = 8.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(
                    BorderStroke(1.dp, Neutral500),
                    shape = RoundedCornerShape(4.dp)
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Kalori",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_regular),
                ),
                fontSize = 18.sp,
                color = Neutral500
            )
            Text(
                text = "400 Kal",
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_semibold),
                ),
                fontSize = 18.sp,
                color = Blue500
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomButton2(
            text = "Simpan",
            color = Blue500,
            contentColor = White,
            onClick = {},
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomButton2(
            text = "Kembali ke Beranda",
            color = Blue50,
            contentColor = Blue700,
            onClick = {
                onBerandaClick()
                photoBitmap = null
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScanLogPreview() {
    CalorifyTheme {
        ScanLogScreen {}
    }
}