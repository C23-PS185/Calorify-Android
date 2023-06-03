package com.calorify.app.ui.screen.profile

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.ui.component.button.CustomButton
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Shapes
import com.calorify.app.ui.theme.White

@Composable
fun MyProfileScreen(
    photoUrl: String,
    name: String,
    email: String,
    birthDate: String,
    age: Int,
    gender: String,
    onButtonClick: () -> Unit,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ){
        Text(
            text = "Profil Saya",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_bold),
            ),
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
        AsyncImage(
            model = photoUrl,
            contentDescription = stringResource(R.string.my_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
                .clip(CircleShape)
        )
        Surface(
            elevation = 20.dp,
            color = White,
            shape = Shapes.medium,
            modifier = Modifier.padding(16.dp)
        ){
            Column(Modifier.padding(vertical=8.dp)){
                ProfileRow(title = "Nama Lengkap", value = name)
                ProfileRow(title = "Email", value = email)
                ProfileRow(title = "Tanggal Lahir", value = birthDate)
                ProfileRow(title = "Umur", value = "$age Tahun")
                ProfileRow(title = "Jenis Kelamin", value = gender)
            }
        }
        CustomButton(icon = R.drawable.ic_pencil, text = "Ubah Profil", onClick = onButtonClick)
    }
}

@Composable
fun ProfileRow(
    title: String,
    value: String,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = FontFamily(
                Font(resId = R.font.inter_semibold),
            ),
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontFamily = FontFamily(
                Font(resId = R.font.inter_regular),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyProfileScreenPreview() {
    CalorifyTheme {
        MyProfileScreen("", "Melati Eka Putri", "melati@gmail.com",  "25 Maret 2002", 21, "Perempuan", {})
    }
}
