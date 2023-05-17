package com.calorify.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.calorify.app.R
import com.calorify.app.ui.fragment.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = LoginFragment()
        val fragment = mFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

        if (fragment !is LoginFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, LoginFragment::class.java.simpleName)
            }
        }
    }
}