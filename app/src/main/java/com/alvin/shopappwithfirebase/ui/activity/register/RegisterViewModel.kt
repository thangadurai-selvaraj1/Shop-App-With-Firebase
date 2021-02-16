package com.alvin.shopappwithfirebase.ui.activity.register

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _apiResponse = MutableLiveData<Resource<AuthResult>>()
    val apiResponse: MutableLiveData<Resource<AuthResult>> = _apiResponse

    var firstName = ObservableField("")
    var lastName = ObservableField("")
    var emailId = ObservableField("")
    var password = ObservableField("")
    var confirmPassword = ObservableField("")

    var firstNameError = ObservableField("")
    var lastNameError = ObservableField("")
    var emailIdError = ObservableField("")
    var passwordError = ObservableField("")
    var confirmPasswordError = ObservableField("")


    fun validation() {
        clearError()
        when {
            !isNullOrEmpty(firstName.get()) -> {
                firstNameError.set("Please Enter First Name")
            }
            !isNullOrEmpty(lastName.get()) -> {
                lastNameError.set("Please Enter First Name")
            }
            !isNullOrEmpty(emailId.get()) -> {
                emailIdError.set("Please Enter Email Id")
            }
            !isNullOrEmpty(password.get()) -> {
                passwordError.set("Please Enter Password")
            }
            !isNullOrEmpty(confirmPassword.get()) -> {
                confirmPasswordError.set("Please Enter Confirm Password")
            }
            password.get() != confirmPassword.get() -> {
                confirmPasswordError.set("Password not matched")
            }
            isNullOrEmpty(firstName.get()) &&
                    isNullOrEmpty(lastName.get()) &&
                    isNullOrEmpty(emailId.get()) &&
                    isNullOrEmpty(password.get()) &&
                    isNullOrEmpty(confirmPassword.get()) &&
                    (password.get() == confirmPassword.get()) -> {

                register()

            }
        }
    }

    private fun clearError() {
        firstNameError.set("")
        lastNameError.set("")
        emailIdError.set("")
        passwordError.set("")
        confirmPasswordError.set("")
    }

    private fun register() {

        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO).launch {

                _apiResponse.postValue(Resource(Status.LOADING, null, null))

                kotlin.runCatching {
                    authRepository.register(emailId.get()!!, password.get()!!)
                }.onSuccess { success ->
                    success.collect {
                        _apiResponse.postValue(it)
                    }
                }.onFailure { failure ->
                    print(failure.message)
                }

            }
        }

    }


}