package com.alvin.shopappwithfirebase.ui.activity.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvin.shopappwithfirebase.data.network.Resource
import com.alvin.shopappwithfirebase.data.network.Status
import com.alvin.shopappwithfirebase.extensions.isNullOrEmpty
import com.alvin.shopappwithfirebase.repo.AuthRepository
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _apiResponse = MutableLiveData<Resource<AuthResult>>(null)
    val apiResponse: MutableLiveData<Resource<AuthResult>> = _apiResponse

    var emailId = ObservableField("")
    var password = ObservableField("")
    var emailIdError = ObservableField("")
    var passwordError = ObservableField("")


    fun validation() {
        clearError()
        when {
            !isNullOrEmpty(emailId.get()) -> {
                emailIdError.set("Please Enter Email Id")
            }
            !isNullOrEmpty(password.get()) -> {
                passwordError.set("Please Enter Password")
            }
            emailId.get() != "" && password.get() != "" -> {
                login()
            }
        }
    }

    private fun clearError() {
        emailIdError.set("")
        passwordError.set("")
    }

    private fun login() {

        viewModelScope.launch(Dispatchers.IO) {
            _apiResponse.postValue(Resource(Status.LOADING, null, null))

            kotlin.runCatching {
                authRepository.login(emailId.get()!!, password.get()!!)
            }.onSuccess { success ->
                success.collect {
                    _apiResponse.postValue(it)
                }
            }.onFailure {
                print(it.message)
            }
        }

    }
}