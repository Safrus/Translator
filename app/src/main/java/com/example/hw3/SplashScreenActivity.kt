package com.example.hw3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    private val splashTimeOut = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val homeIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(homeIntent)
            finish()
        }, splashTimeOut.toLong())
    }
}
