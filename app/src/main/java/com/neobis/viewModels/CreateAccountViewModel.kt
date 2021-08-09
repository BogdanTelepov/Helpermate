package com.neobis.viewModels

import androidx.lifecycle.*
import com.neobis.models.auth.*
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

    private var _registerStepOneResponse: MutableLiveData<NetworkResult<RegisterStepOneResponse>> =
        MutableLiveData()

    val registerStepOneResponse: LiveData<NetworkResult<RegisterStepOneResponse>> get() = _registerStepOneResponse


    private var _registerStepTwoResponse: MutableLiveData<NetworkResult<RegisterStepTwoResponse>> =
        MutableLiveData()
    val registerStepTwoResponse: LiveData<NetworkResult<RegisterStepTwoResponse>> get() = _registerStepTwoResponse


    private var _registerStepThreeResponse: MutableLiveData<NetworkResult<RegisterStepThreeResponse>> =
        MutableLiveData()
    val registerStepThreeResponse: LiveData<NetworkResult<RegisterStepThreeResponse>> get() = _registerStepThreeResponse


    fun createNewAccount(registerStepOneRequest: RegisterStepOneRequest) {
        viewModelScope.launch {
            registerUser(registerStepOneRequest)
        }
    }


    private suspend fun registerUser(registerStepOneRequest: RegisterStepOneRequest) {
        _registerStepOneResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.authRepository.registrationStepOne(registerStepOneRequest)
                _registerStepOneResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _registerStepOneResponse.value = NetworkResult.Error("Что то пошло не так")
            }


        } else {
            _registerStepOneResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }

    fun registerStepTwo(registerStepTwoRequest: RegisterStepTwoRequest) {
        viewModelScope.launch {
            registerUserStepTwo(registerStepTwoRequest)
        }
    }

    private suspend fun registerUserStepTwo(registerStepTwoRequest: RegisterStepTwoRequest) {
        _registerStepTwoResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.authRepository.registrationStepTwo(registerStepTwoRequest)
                _registerStepTwoResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _registerStepTwoResponse.value = NetworkResult.Error("Что то пошло не так")
            }
        } else {
            _registerStepTwoResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }

    fun registerStepThree(registerStepThreeRequest: RegisterStepThreeRequest) {
        viewModelScope.launch {
            registerUserStepThree(registerStepThreeRequest)
        }
    }

    private suspend fun registerUserStepThree(registerStepThreeRequest: RegisterStepThreeRequest) {
        _registerStepThreeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.authRepository.registrationStepThree(registerStepThreeRequest)
                _registerStepThreeResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _registerStepThreeResponse.value = NetworkResult.Error("Что то пошло не так")
            }
        } else {
            _registerStepThreeResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }


    private fun <T> handleResponse(response: Response<T>): NetworkResult<T> {

        return when {
            response.isSuccessful -> {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            }

            response.code() == 400 -> {
                NetworkResult.Error(response.message())
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