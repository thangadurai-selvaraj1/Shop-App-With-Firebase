package com.alvin.shopappwithfirebase.ui.activity.base

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.alvin.shopappwithfirebase.R

open class BaseActivity : AppCompatActivity() {

    lateinit var progressDialog: Dialog
    fun showProgress() {
        progressDialog=  Dialog(this)

        progressDialog.apply {

            setContentView(R.layout.dialog_progress)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }

    }

    fun hideProgress() {
        progressDialog.apply {
            dismiss()
        }
    }

}