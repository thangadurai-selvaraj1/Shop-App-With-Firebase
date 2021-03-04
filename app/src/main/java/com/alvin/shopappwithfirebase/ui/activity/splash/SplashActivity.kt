package com.alvin.shopappwithfirebase.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alvin.shopappwithfirebase.databinding.ActivitySplashBinding
import com.alvin.shopappwithfirebase.ui.activity.login.LoginActivity
import com.alvin.shopappwithfirebase.ui.activity.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLoggedInState()
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            launchScreen(LoginActivity::class.java)
        } else {
            launchScreen(MainActivity::class.java)
        }
    }

    private fun launchScreen(java: Class<out Any>) {
        lifecycleScope.launch {
            delay(2000L)
            startActivity(Intent(this@SplashActivity, java))
            finish()
        }
    }

}