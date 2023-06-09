package com.calorify.app.ui.screen.profile

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.databinding.AssessmentResultScreenBinding

@Composable
fun SelfAssessmentResultScreen(
    weight: String,
    height: String,
    indexBmi: Float,
    weightGoal: String,
    calorie: Int,
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

            when(indexBmi) {
                in Float.MIN_VALUE..18.4f -> binding.indicatorUnderweight.visibility = View.VISIBLE
                in 18.5f..24.9f-> binding.indicatorNormal.visibility = View.VISIBLE
                in 25.0f..29.9f -> binding.indicatorOverweight.visibility = View.VISIBLE
                in 30.0f..Float.MAX_VALUE -> binding.indicatorObesitas.visibility = View.VISIBLE
                else -> binding.indicatorObesitas.visibility = View.VISIBLE
            }

            binding.buttonDoAssessment.setOnClickListener{
                onDoAssessmentClick()
            }


        binding.root
    })
}