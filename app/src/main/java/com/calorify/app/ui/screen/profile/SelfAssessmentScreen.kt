package com.calorify.app.ui.screen.profile

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.databinding.AssessmentScreenBinding
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun SelfAssessmentScreen(
) {
    AndroidView(
        modifier = Modifier.padding(8.dp).fillMaxSize(),
        factory = { context ->
        val binding = AssessmentScreenBinding.inflate(LayoutInflater.from(context))
        binding.root
    })
}

@Preview(showBackground = true)
@Composable
fun SelfAssessmentScreenPreview() {
    CalorifyTheme {
        SelfAssessmentScreen()
    }
}