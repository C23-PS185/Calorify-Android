package com.calorify.app.ui.screen.profile

import android.view.LayoutInflater
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.databinding.AssessmentResultScreenBinding
import com.calorify.app.ui.theme.CalorifyTheme

@Composable
fun SelfAssessmentResultScreen(
    onDoAssessmentClick: () -> Unit,
) {
    AndroidView(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        factory = { context ->
        val binding = AssessmentResultScreenBinding.inflate(LayoutInflater.from(context))

            binding.tvWeightValue.text = "60 kg"
            binding.tvHeightValue.text = "162 cm"
            binding.tvIndexBmiValue.text = "20.2"
            binding.tvTujuanValue.text = "Mempertahankan berat badan"
            binding.tvCaloryValue.text = "2455"
            binding.buttonDoAssessment.setOnClickListener{
                onDoAssessmentClick()
            }


        binding.root
    })
}

@Preview(showBackground = true)
@Composable
fun SelfAssessmentResultScreenPreview() {
    CalorifyTheme {
        SelfAssessmentResultScreen {}
    }
}