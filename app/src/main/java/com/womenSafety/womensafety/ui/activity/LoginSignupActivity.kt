package com.womenSafety.womensafety.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.womenSafety.womensafety.R
import com.womenSafety.womensafety.ui.fragment.WelcomeScreenFragment

class LoginSignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        supportFragmentManager.beginTransaction().replace(R.id.container, WelcomeScreenFragment()).commit()
    }
}