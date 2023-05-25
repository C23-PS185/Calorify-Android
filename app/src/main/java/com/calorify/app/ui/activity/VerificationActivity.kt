package com.calorify.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.calorify.app.R
import com.calorify.app.databinding.ActivityLoginBinding
import com.calorify.app.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {

    private var _binding: ActivityVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}