package com.calorify.app.ui.screen.scan

import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.R
import com.calorify.app.databinding.ScanResultScreenBinding

@Composable
fun ScanResultScreen(
    onBerandaClick: () -> Unit,
    onScanLogClick: () -> Unit
) {
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
            if (photoBitmap != null) {
                imageViewResult.setImageBitmap(photoBitmap)
            }
            Log.d("photoBitmap", "ScanCalorieScreen: $photoBitmap")
        }
    )
}