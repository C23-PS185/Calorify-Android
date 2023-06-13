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
import com.calorify.app.databinding.ScanResultScreenBinding
import com.calorify.app.helper.TensorFLowHelper
import com.calorify.app.helper.TensorFLowHelper.imageSize

var foodName by mutableStateOf<String?>(null)
@Composable
fun ScanResultScreen(
    onBerandaClick: () -> Unit,
    onScanLogClick: () -> Unit
) {
    photoBitmap?.let {
        val scaledBeatmap = Bitmap.createScaledBitmap(it, imageSize, imageSize, false)
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
            val textViewResult = view.findViewById<TextView>(R.id.tv_ads)
            if (photoBitmap != null) {
                imageViewResult.setImageBitmap(photoBitmap)
                textViewResult.text = foodName
            }
            Log.d("photoBitmap", "ScanCalorieScreen: $photoBitmap")
        }
    )
}