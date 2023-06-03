package com.calorify.app.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.ui.theme.Blue50
import com.calorify.app.ui.theme.CalorifyTheme
import com.calorify.app.ui.theme.Shapes

@Composable
fun ProfileScreen(
    photoUrl : String,
    name: String,
    email: String,
    onMyProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onSelfAssessmentResultClick: () -> Unit,
    onPremiumSubscriptionClick: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = stringResource(R.string.my_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = name,
                fontSize = 24.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_semibold),
                ),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = email,
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(resId = R.font.inter_regular),
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            ProfileMenu(icon = R.drawable.ic_people, description = "Icon profile", text = "Profil Saya", onClick = onMyProfileClick)
            ProfileMenu(icon = R.drawable.ic_confirm_lock, description = "Icon password", text = "Ubah Kata Sandi", onClick = onChangePasswordClick)
            ProfileMenu(icon = R.drawable.ic_weight, description = "Icon Weight", text = "Hasil Asesmen Diri", onClick = onSelfAssessmentResultClick)
            ProfileMenu(icon = R.drawable.ic_grey_star, description = "Icon Subsription", text = "Berlangganan Premium", onClick = onPremiumSubscriptionClick)
            ProfileMenu(icon = R.drawable.ic_logout, description = "Icon Logout", text = "Keluar", onClick = onSignOut)

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileMenu(
    icon: Int,
    description: String,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        color = Blue50,
        modifier = modifier.padding(top=16.dp),
        shape = Shapes.medium,
        onClick = onClick
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = description
                )
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(resId = R.font.inter_regular),
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Icon Panah"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CalorifyTheme {
        ProfileScreen("", "Melati", "melati@gmail.com", {}, {}, {}, {}, {})
    }
}
