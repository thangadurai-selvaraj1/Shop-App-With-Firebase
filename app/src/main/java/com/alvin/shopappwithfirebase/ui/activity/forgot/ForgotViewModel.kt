package com.alvin.shopappwithfirebase.ui.activity.forgot

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvin.shopappwithfirebase.data.network.Resource
import com.alvin.shopappwithfirebase.data.network.Status
import com.alvin.shopappwithfirebase.extensions.isNullOrEmpty
import com.alvin.shopappwithfirebase.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _apiResponse = MutableLiveData<Resource<Void>>(null)
    val apiResponse: MutableLiveData<Resource<Void>> = _apiResponse

    var emailId = ObservableField("")
    var emailIdError = ObservableField("")


    fun validation() {
        clearError()
        when {
            !isNullOrEmpty(emailId.get()) -> {
                emailIdError.set("Please Enter Email Id")
            }

            emailId.get() != "" -> {
                login()
            }
        }
    }

    private fun clearError() {
        emailIdError.set("")
    }

    private fun login() {

        viewModelScope.launch(Dispatchers.IO) {
            _apiResponse.postValue(Resource(Status.LOADING, null, null))

            kotlin.runCatching {
                authRepository.forgot(emailId.get()!!)
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