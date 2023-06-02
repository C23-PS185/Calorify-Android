package com.calorify.app.ui.screen

import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.R

@Composable
fun ChangePasswordScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.scan_calorie_screen, null)
            view.findViewById<TextView>(R.id.tv_title).text = "Ubah Password"
            view
        }
    )
}