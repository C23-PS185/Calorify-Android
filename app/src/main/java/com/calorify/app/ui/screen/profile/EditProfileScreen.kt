package com.calorify.app.ui.screen.profile

import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults.IconSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.databinding.EditProfileScreenBinding
import com.calorify.app.ui.theme.Black
import com.calorify.app.ui.theme.CalorifyTheme
import androidx.compose.material.ButtonDefaults.IconSize
import androidx.compose.material.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.calorify.app.ui.theme.White

@Composable
fun EditProfileScreen(
    photoUrl : String,
    name: String,
    birthDate: String,
    gender: String,
) {
    var newGender by remember { mutableStateOf(gender) }
    var newName by remember { mutableStateOf(name) }
    var newBirthDate by remember { mutableStateOf(birthDate) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Ubah Profil",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_bold),
            ),
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(8.dp)
                .size(200.dp)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = stringResource(R.string.my_photo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Black.copy(alpha = 0.5f),
                    radius = size.minDimension / 2
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_pencil),
                contentDescription = "Edit Image",
                tint = White,
            )
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                val binding = EditProfileScreenBinding.inflate(LayoutInflater.from(context))
                binding.etName.setText(name)
                binding.etBirth.setText(birthDate)

                if (gender == "Perempuan") {
                    binding.radioFemale.isChecked = true
                } else {
                    binding.radioMale.isChecked = true
                }

                val onRadioButtonClicked: (View) -> Unit = { view ->
                    if (view is RadioButton) {
                        val checked = view.isChecked

                        when (view.id) {
                            R.id.radio_female -> {
                                if (checked) {
                                    newGender = "Perempuan"
                                }
                            }

                            R.id.radio_male -> {
                                if (checked) {
                                    newGender = "Laki-Laki"
                                }
                            }

                            else -> {
                                newGender = gender
                            }
                        }
                    }
                }

                binding.radioFemale.setOnClickListener(onRadioButtonClicked)
                binding.radioMale.setOnClickListener(onRadioButtonClicked)

                binding.root
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    CalorifyTheme {
        EditProfileScreen("", "Melati Eka Putri", "25/03/2002", "Perempuan")
    }
}