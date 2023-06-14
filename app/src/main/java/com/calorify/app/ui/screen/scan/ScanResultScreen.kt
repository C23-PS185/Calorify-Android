package com.calorify.app.ui.screen.scan

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.R
import com.calorify.app.data.local.CalorieDict
import com.calorify.app.data.local.FoodDict
import com.calorify.app.data.local.ServingDict
import com.calorify.app.databinding.ScanResultScreenBinding
import com.calorify.app.helper.TensorFLowHelper
import com.calorify.app.helper.TensorFLowHelper.imageSize

var foodName by mutableStateOf("")
var foodCalorie by mutableStateOf("")
var servingCalorie by mutableStateOf("")

@Composable
fun ScanResultScreen(
    onBerandaClick: () -> Unit,
    onScanLogClick: () -> Unit
) {
    photoBitmap?.let { bitmap ->
        val scaledBeatmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
        TensorFLowHelper.ClassifyImage(scaledBeatmap) {
            foodName = it
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val binding = ScanResultScreenBinding.inflate(LayoutInflater.from(context))

            binding.buttonAddLog.setOnClickListener {
                onScanLogClick()
            }

            binding.buttonBeranda.setOnClickListener {
                photoBitmap = null
                onBerandaClick()
            }
            binding.root
        },
        update = { view ->
            // update view
            val imageViewResult = view.findViewById<ImageView>(R.id.imageScanResult)
            val textViewFoodResult = view.findViewById<TextView>(R.id.tv_ads)
            val textViewCalorieResult = view.findViewById<TextView>(R.id.tv_food_kal)
            val textViewServingResult = view.findViewById<TextView>(R.id.tv_porsi_value)

            if (photoBitmap != null) {
                foodCalorie = CalorieDict.foodToKal[foodName].toString()
                servingCalorie = ServingDict.foodToServing[foodName].toString()

                imageViewResult.setImageBitmap(photoBitmap)
                textViewFoodResult.text = FoodDict.wordMap[foodName]
                textViewCalorieResult.text = "$foodCalorie kal"
                textViewServingResult.text = "($servingCalorie g)"
            }
            Log.d("photoBitmap", "ScanCalorieScreen: $photoBitmap")
        }
    )
}