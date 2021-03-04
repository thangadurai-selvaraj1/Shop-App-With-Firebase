package com.alvin.shopappwithfirebase.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.alvin.shopappwithfirebase.R
import com.alvin.shopappwithfirebase.data.network.Status
import com.alvin.shopappwithfirebase.databinding.ActivityLoginBinding
import com.alvin.shopappwithfirebase.ui.activity.base.BaseActivity
import com.alvin.shopappwithfirebase.ui.activity.forgot.ForgotActivity
import com.alvin.shopappwithfirebase.ui.activity.main.MainActivity
import com.alvin.shopappwithfirebase.ui.activity.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity(), View.OnClickListener {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.listener = this

        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel


        loginObserver()
    }


    private fun loginObserver() {
        loginViewModel.apiResponse.observe(this, { result ->
            result?.status?.let {
                when (it) {
                    Status.SUCCESS -> {
                        hideProgress()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    Status.ERROR -> {
                        hideProgress()
                        showToast(
                            result.message.toString()
                        )
                    }
                    Status.LOADING -> {
                        showProgress()
                    }

                }

            }

        })
    }

    override fun onClick(v: View) {
        when (v) {
            binding.tvForgot -> {
                startActivity(Intent(this@LoginActivity, ForgotActivity::class.java))
            }
            binding.tvRegister -> {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}