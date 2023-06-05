package com.calorify.app.ui.screen.scan

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.databinding.ScanResultScreenBinding

@Composable
fun ScanResultScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val binding = ScanResultScreenBinding.inflate(LayoutInflater.from(context))
            binding.root
        }
    )
}