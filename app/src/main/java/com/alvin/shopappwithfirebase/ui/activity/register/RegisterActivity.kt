package com.alvin.shopappwithfirebase.ui.activity.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.alvin.shopappwithfirebase.R
import com.alvin.shopappwithfirebase.data.model.User
import com.alvin.shopappwithfirebase.data.network.Status
import com.alvin.shopappwithfirebase.databinding.ActivityRegisterBinding
import com.alvin.shopappwithfirebase.ui.activity.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity(), View.OnClickListener {

    private val registerViewModel by viewModels<RegisterViewModel>()
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        binding.registerViewModel = registerViewModel

        registerObserver()
        userObserver()
    }


    private fun registerObserver() {
        registerViewModel.apiResponse.observe(this, { result ->
            result?.status?.let {
                when (it) {
                    Status.SUCCESS -> {
                        result.data?.user?.let { user ->
                            registerViewModel.addUserCollection(
                                User(
                                    user.uid,
                                    registerViewModel.firstName.get()!!,
                                    registerViewModel.lastName.get()!!,
                                    registerViewModel.emailId.get()!!,
                                    "",
                                    "",
                                    "",
                                    0
                                )
                            )
                        }
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

    private fun userObserver() {
        registerViewModel.apiResponseUser.observe(this, { result ->
            result?.status?.let {
                when (it) {
                    Status.SUCCESS -> {
                        hideProgress()
                        hideProgress()

                        showToast("Registration Successfully")
                        finish()
                    }
                    Status.ERROR -> {
                        hideProgress()
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

    override fun onClick(v: View?) {
        when (v) {

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}