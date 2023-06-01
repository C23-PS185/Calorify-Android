package com.calorify.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.calorify.app.R
import com.calorify.app.databinding.ActivityAssessmentResultBinding
import com.calorify.app.databinding.ActivityLoginBinding

class AssessmentResultActivity : AppCompatActivity() {
    private var _binding: ActivityAssessmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAssessmentResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}