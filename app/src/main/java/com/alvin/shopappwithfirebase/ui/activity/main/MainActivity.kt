package com.alvin.shopappwithfirebase.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alvin.shopappwithfirebase.R
import com.alvin.shopappwithfirebase.ui.activity.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_main).setOnClickListener {
            auth.signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}