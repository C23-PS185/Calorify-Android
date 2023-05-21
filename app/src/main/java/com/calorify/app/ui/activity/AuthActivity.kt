package com.calorify.app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.calorify.app.R
import com.calorify.app.ui.fragment.RegisterFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportActionBar?.hide()

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = RegisterFragment()
        val fragment = mFragmentManager.findFragmentByTag(RegisterFragment::class.java.simpleName)

        if (fragment !is RegisterFragment) {
            mFragmentManager.commit {
                add(R.id.frame_container, mHomeFragment, RegisterFragment::class.java.simpleName)
            }
        }
    }
}