package com.neobis.ui.activities.registration

import android.app.Application
import androidx.lifecycle.*
import com.neobis.models.auth.RegisterRequest
import com.neobis.models.auth.RegisterResponse
import com.neobis.repository.Repository
import com.neobis.utils.Functions.Companion.hasInternetConnection
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _registerResponse: MutableLiveData<NetworkResult<RegisterResponse>> =
        MutableLiveData()

    val registerResponse: LiveData<NetworkResult<RegisterResponse>> get() = _registerResponse


    fun createNewAccount(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUser(registerRequest)
        }
    }


    private suspend fun registerUser(registerRequest: RegisterRequest) {
        _registerResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.register(registerRequest)
                _registerResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _registerResponse.value = NetworkResult.Error("Что то пошло не так")
            }


        } else {
            _registerResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }


    private fun <T> handleResponse(response: Response<T>): NetworkResult<T> {

        return when {
            response.isSuccessful -> {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            }

            response.message().toString().contains("account with this email already exists") -> {
                NetworkResult.Error("Аккаунт с такой почтой уже существует")
            }

            response.message().toString().contains("Password fields didn't match") -> {
                NetworkResult.Error("Пароли не совпадают")
            }
            response.message().toString().contains("Enter a valid email address") -> {
                NetworkResult.Error("Введите корректную почту")
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }


}