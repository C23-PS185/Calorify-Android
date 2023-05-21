package com.calorify.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.calorify.app.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_onboarding)
    }
}