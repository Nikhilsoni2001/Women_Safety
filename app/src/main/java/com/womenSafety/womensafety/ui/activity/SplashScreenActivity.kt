package com.womenSafety.womensafety.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import com.womenSafety.womensafety.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var logo: ImageView
    private lateinit var appName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        logo = findViewById(R.id.logo)
        appName = findViewById(R.id.appName)

        val sideAnim: Animation =
            android.view.animation.AnimationUtils.loadAnimation(this, R.anim.side_anim)
        val bottomAnim: Animation =
            android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        logo.animation = sideAnim
        logo.animation = bottomAnim
        appName.animation = sideAnim
        appName.animation = bottomAnim

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val intent = Intent(this@SplashScreenActivity, LoginSignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}