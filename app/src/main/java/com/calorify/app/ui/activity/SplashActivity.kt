package com.calorify.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.calorify.app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}