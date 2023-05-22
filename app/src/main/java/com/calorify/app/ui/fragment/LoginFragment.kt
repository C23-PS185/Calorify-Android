package com.calorify.app.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.calorify.app.R
import com.calorify.app.databinding.FragmentLoginBinding
import com.calorify.app.viewmodel.LoginViewModel
import com.calorify.app.viewmodel.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegister.setOnClickListener {
            switchToRegister()
        }

//        binding.loginButton.setOnClickListener {
//            authentication()
//        }
    }

    private fun switchToRegister() {
        val mRegisterFragment = RegisterFragment()
        val mFragmentManager = parentFragmentManager

        mFragmentManager.commit {
            replace(
                R.id.frame_container, mRegisterFragment, RegisterFragment::class.java.simpleName
            )
            addToBackStack(null)
        }
    }

//    private fun authentication() {
//        binding.apply {
//            val etEmail = emailEditTextLayout..toString()
//            val etPassword = passEditTextLayout..toString()
//            val isValid = emailEditTextLayout.error == null && passEditTextLayout.error == null
//
//            when {
//                etEmail.isEmpty() -> {
//                    customETemail.error = resources.getString(R.string.fill_email)
//                }
//                etPassword.isEmpty() -> {
//                    customETpass.error = resources.getString(R.string.fill_password)
//                }
//                isValid -> {
//                    loginViewModel.login(etEmail, etPassword).observe(requireActivity()) { result ->
//                        when (result) {
//                            is Result.Loading -> {
//                                progressBar.visibility = View.VISIBLE
//                                loginButton.isEnabled = false
//                            }
//
//                            is Result.Success -> {
//                                progressBar.visibility = View.GONE
//                                loginButton.isEnabled = true
//                                activity?.let {
//                                    val intent = Intent(it, MainActivity::class.java)
//                                    startActivity(intent)
//                                    it.finish()
//                                }
//                            }
//
//                            is Result.Error -> {
//                                progressBar.visibility = View.GONE
//                                loginButton.isEnabled = true
//                                Toast.makeText(requireActivity(), result.error, Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}