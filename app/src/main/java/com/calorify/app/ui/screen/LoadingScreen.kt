package com.calorify.app.ui.screen

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.databinding.AssessmentResultScreenBinding
import com.calorify.app.databinding.LoadingScreenBinding
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun LoadingScreen() {
    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { context ->
            val binding = LoadingScreenBinding.inflate(LayoutInflater.from(context))
            binding.root
        })
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    CalorifyTheme {
        LoadingScreen()
    }
}