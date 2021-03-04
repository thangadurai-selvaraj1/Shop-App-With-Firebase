package com.alvin.shopappwithfirebase.ui.activity.forgot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.alvin.shopappwithfirebase.R
import com.alvin.shopappwithfirebase.data.network.Status
import com.alvin.shopappwithfirebase.databinding.ActivityForgotBinding
import com.alvin.shopappwithfirebase.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotActivity : BaseActivity() {

    private val forgotViewModel by viewModels<ForgotViewModel>()
    private lateinit var binding: ActivityForgotBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_forgot)

        binding.lifecycleOwner = this
        binding.forgotViewModel = forgotViewModel


        forgotObserver()
    }


    private fun forgotObserver() {
        forgotViewModel.apiResponse.observe(this, { result ->
            result?.status?.let {
                when (it) {
                    Status.SUCCESS -> {
                        hideProgress()
                        showToast(
                            "Email Sent successfully"
                        )
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


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}