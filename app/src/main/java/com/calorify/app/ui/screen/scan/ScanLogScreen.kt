package com.calorify.app.ui.screen.scan

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.calorify.app.R
import com.calorify.app.data.local.FoodDict
import com.calorify.app.data.remote.request.CalorieLogRequest
import com.calorify.app.helper.Result
import com.calorify.app.ui.component.button.CustomButton2
import com.calorify.app.ui.component.input.MealTimeSelection
import com.calorify.app.ui.component.input.RadioButtonType
import com.calorify.app.ui.theme.Blue50
import com.calorify.app.ui.theme.Blue500
import com.calorify.app.ui.theme.Blue700
import com.calorify.app.ui.theme.Neutral500
import com.calorify.app.ui.theme.White
import com.calorify.app.viewmodel.AddCalorieLogViewModel

@Composable
fun ScanLogScreen(
    onBerandaClick: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: AddCalorieLogViewModel,
    userId: String
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val foodNameState by remember { mutableStateOf(foodName) }
    var fnbTypeState by remember { mutableStateOf("Makanan") }
    var selectedMealTime by remember { mutableStateOf("Sarapan") }
    var selectedMealTimeIndex by remember { mutableStateOf(0) }
    var foodPortionState by remember { mutableStateOf("1") }
    val foodCalorieState by remember { mutableStateOf(foodCalorie) }
    var foodCalorieStateTotal by remember { mutableStateOf(foodCalorieState) }

    selectedMealTimeIndex = when (selectedMealTime) {
        "Sarapan" -> 0
        "Makan Siang" -> 1
        "Makan Malam" -> 2
        "Lain-Lain" -> 3
        else -> 0
    }

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
            value = FoodDict.wordMap[foodNameState]!!,
            onValueChange = { },
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
        RadioButtonType(Modifier.padding(bottom = 8.dp)) {
            fnbTypeState = it
        }
        Text(
            text = "Waktu Makan",
            fontFamily = FontFamily(
                Font(resId = R.font.inter_medium),
            ),
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Start)
        )
        MealTimeSelection(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            MealTimeSelected = selectedMealTime
        ) { newMealTime ->
            selectedMealTime = newMealTime
            Log.d("MealTimeSelected", "selectedMealTime: $selectedMealTime")
        }
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
            value = foodPortionState,
            onValueChange = { newInput ->
                foodPortionState = newInput
                foodCalorieStateTotal = foodCalorieTotal(foodPortionState, foodCalorieState)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
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
                text = "$foodCalorieStateTotal kal",
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
            onClick = {
                val isValid =
                    foodNameState.isNotEmpty() && fnbTypeState.isNotEmpty() && foodCalorieState.isNotEmpty()

                val body = CalorieLogRequest(
                    foodName = foodNameState,
                    fnbType = fnbTypeState,
                    foodCalories = foodCalorieStateTotal.toInt(),
                    mealTime = selectedMealTimeIndex
                )

                if (isValid) {
                    viewModel.setCalorieLogData(body)
                    viewModel.uploadCalorieLog(userId).observe(lifecycleOwner) { result ->
                        when (result) {
                            is Result.Success -> {
                                onSuccess()
                                onBerandaClick()
                                photoBitmap = null
                            }

                            is Result.Error -> {
                                Toast.makeText(
                                    context,
                                    result.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is Result.Loading -> {
                                Log.d("CalorieLog", "Loading")
                            }
                        }
                    }
                }
            },
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

private fun foodCalorieTotal(foodCalorieState: String, foodPortionState: String) =
    (foodCalorieState.toIntOrNull()?.times(foodPortionState.toIntOrNull()!!)).toString()