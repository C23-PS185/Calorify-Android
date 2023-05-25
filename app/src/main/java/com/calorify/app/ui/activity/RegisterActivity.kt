package com.calorify.app.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.calorify.app.R
import com.calorify.app.data.remote.request.RegisterRequest
import com.calorify.app.databinding.ActivityRegisterBinding
import com.calorify.app.viewmodel.RegisterViewModel
import com.calorify.app.viewmodel.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.calorify.app.helper.Result

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Button Click Listener
        binding.registerButton.setOnClickListener {
            authentication()
        }

        binding.registerButtonGmail.setOnClickListener {
            signIn()
        }

        binding.tvLogin.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    private fun authentication() {
        val etEmail = binding.etEmail.text.toString()
        val etPass = binding.etPass.text.toString()
        val etConfirmPass = binding.etConfirmPass.text.toString()
        val isValid = binding.etEmail.error == null && binding.etPass.error == null && binding.etConfirmPass.error == null

        when {
            etEmail.isEmpty() -> {
                binding.etEmail.error = resources.getString(R.string.fill_email)
            }

            etPass.isEmpty() -> {
                binding.etPass.error = resources.getString(R.string.fill_pass)
            }

            etConfirmPass.isEmpty() -> {
                binding.etConfirmPass.error = resources.getString(R.string.fill_confirm_pass)
            }

            etPass != etConfirmPass -> {
                binding.etConfirmPass.error = resources.getString(R.string.confirm_pass_not_match)
            }

            isValid -> {
                val body = RegisterRequest(
                    email = etEmail,
                    password = etPass,
                    passwordConfirmation = etConfirmPass
                )
                registerViewModel.setRegisterInfo(body)
                registerViewModel.register().observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.registerButton.isEnabled = true
                            Toast.makeText(this@RegisterActivity, result.error, Toast.LENGTH_SHORT).show()
                        }

                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.registerButton.isEnabled = false
                        }
                    }
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }
    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}