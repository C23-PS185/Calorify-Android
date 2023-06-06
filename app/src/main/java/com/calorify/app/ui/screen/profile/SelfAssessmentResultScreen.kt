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
    weight: String,
    height: String,
    indexBmi: Float,
    weightGoal: String,
    calorie: Float,
    onDoAssessmentClick: () -> Unit,
) {
    AndroidView(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        factory = { context ->
        val binding = AssessmentResultScreenBinding.inflate(LayoutInflater.from(context))

            binding.tvWeightValue.text = "$weight kg"
            binding.tvHeightValue.text = "$height cm"
            binding.tvIndexBmiValue.text = indexBmi.toString()
            binding.tvTujuanValue.text = weightGoal
            binding.tvCaloryValue.text = calorie.toString()
            binding.buttonDoAssessment.setOnClickListener{
                onDoAssessmentClick()
            }


        binding.root
    })
}