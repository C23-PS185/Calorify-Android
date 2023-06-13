package com.calorify.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.calorify.app.databinding.ActivityMainBinding
import com.calorify.app.helper.NetworkManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        if(NetworkManager.isConnectedToNetwork(this)){
            super.onCreate(savedInstanceState)
            _binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            auth = Firebase.auth
            val firebaseUser = auth.currentUser

            if (firebaseUser == null) {
                // Not signed in, launch the Login activity
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return
            } else {
                if (firebaseUser.isEmailVerified) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    return
                } else {
                    startActivity(Intent(this, VerificationActivity::class.java))
                    finish()
                    return
                }
            }
        } else {
            val i = Intent(this, NoConnectionActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }
}