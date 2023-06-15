package com.calorify.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.calorify.app.databinding.ActivityVerificationBinding
import com.calorify.app.helper.NetworkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        if (NetworkManager.isConnectedToNetwork(this)) {
            super.onCreate(savedInstanceState)
            binding = ActivityVerificationBinding.inflate(layoutInflater)
            setContentView(binding.root)

            auth = Firebase.auth
            currentUser = auth.currentUser!!

            currentUser.sendEmailVerification()

            binding.verificationButton.setOnClickListener {
                currentUser.reload().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        currentUser = auth.currentUser!!
                        if (currentUser.isEmailVerified) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Emailmu belum terverifikasi.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(this, "Gagal mengambil data pengguna.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            binding.tvResend.setOnClickListener {
                currentUser.sendEmailVerification()
            }
        } else {
            val i = Intent(this, NoConnectionActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }

    }
}