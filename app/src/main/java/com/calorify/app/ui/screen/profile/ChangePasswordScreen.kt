package com.calorify.app.ui.screen.profile

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.calorify.app.R
import com.calorify.app.databinding.AssessmentResultScreenBinding
import com.calorify.app.databinding.ChangePasswordScreenBinding
import com.calorify.app.ui.activity.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(
    onSaveButtonClick: (String, String) -> String,
) {

    var oldPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    AndroidView(
        modifier = Modifier.padding(8.dp).fillMaxSize(),
        factory = { context ->
            val binding = ChangePasswordScreenBinding.inflate(LayoutInflater.from(context))

            binding.saveButton.setOnClickListener{
                oldPass = binding.etOldPass.text.toString()
                newPass = binding.etNewPass.text.toString()
                confirmPass = binding.etConfirmPass.text.toString()
                val isValid = binding.etOldPass.error == null && binding.etNewPass.error == null && binding.etConfirmPass.error == null

                when {

                    oldPass == ""-> {
                        binding.etOldPass.error = "Tolong masukkan password baru"
                    }

                    newPass == ""-> {
                        binding.etNewPass.error = "Tolong masukkan password baru"
                    }

                    confirmPass == "" -> {
                        binding.etConfirmPass.error = "Tolong masukkan konfirmasi password baru"
                    }

                    newPass != confirmPass -> {
                        binding.etConfirmPass.error = "Konfirmasi password tidak cocok"
                    }

                    isValid -> {
                        val msg = onSaveButtonClick(newPass, oldPass)
                        CoroutineScope(Dispatchers.Main).launch {
                            // Show the toast message
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            binding.root
        }
    )
}