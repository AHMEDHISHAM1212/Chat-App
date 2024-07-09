package com.example.wechat.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.wechat.databinding.ActivitySplashBinding
import com.example.wechat.ui.login.LoginActivity
import com.example.wechat.ui.register.RegisterActivity

class SplashActivity: AppCompatActivity(){
    private lateinit var viewBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initSplashScreen()

    }
    private fun initSplashScreen() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity()}
            ,20000)
    }

    private fun startActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}