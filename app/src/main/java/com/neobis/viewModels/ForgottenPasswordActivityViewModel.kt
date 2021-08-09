package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
class ForgottenPasswordActivityViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private var _restorePasswordStepOneResponse: MutableLiveData<NetworkResult<RestorePasswordStepOneResponse>> =
        MutableLiveData()
    val restorePasswordStepOneResponse: LiveData<NetworkResult<RestorePasswordStepOneResponse>> get() = _restorePasswordStepOneResponse

    private var _restorePasswordStepTwoResponse: MutableLiveData<NetworkResult<RestorePasswordStepTwoResponse>> =
        MutableLiveData()
    val restorePasswordStepTwoResponse: LiveData<NetworkResult<RestorePasswordStepTwoResponse>> get() = _restorePasswordStepTwoResponse

    private var _restorePasswordStepThreeResponse: MutableLiveData<NetworkResult<RestorePasswordStepThreeResponse>> =
        MutableLiveData()
    val restorePasswordStepThreeResponse: LiveData<NetworkResult<RestorePasswordStepThreeResponse>> get() = _restorePasswordStepThreeResponse


    fun restorePasswordStepOne(restorePasswordStepOneRequest: RestorePasswordStepOneRequest) {
        viewModelScope.launch {
            restoreStepOne(restorePasswordStepOneRequest)
        }
    }


    private suspend fun restoreStepOne(restorePasswordStepOneRequest: RestorePasswordStepOneRequest) {
        _restorePasswordStepOneResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response =
                    repository.authRepository.restorePasswordStepOne(restorePasswordStepOneRequest)
                _restorePasswordStepOneResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _restorePasswordStepOneResponse.value =
                    NetworkResult.Error("Что то пошло не так :(")
            }

        } else {
            _restorePasswordStepOneResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }

    fun restorePasswordStepTwo(restorePasswordStepTwoRequest: RestorePasswordStepTwoRequest) {
        viewModelScope.launch {
            restoreStepTwo(restorePasswordStepTwoRequest)
        }
    }

    private suspend fun restoreStepTwo(restorePasswordStepTwoRequest: RestorePasswordStepTwoRequest) {
        _restorePasswordStepTwoResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response =
                    repository.authRepository.restorePasswordStepTwo(restorePasswordStepTwoRequest)
                _restorePasswordStepTwoResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _restorePasswordStepTwoResponse.value =
                    NetworkResult.Error("Что то пошло не так :(")
            }
        } else {
            _restorePasswordStepTwoResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }

    fun restorePasswordStepThree(restorePasswordStepThreeRequest: RestorePasswordStepThreeRequest) {
        viewModelScope.launch {
            restoreStepThree(restorePasswordStepThreeRequest)
        }
    }

    private suspend fun restoreStepThree(restorePasswordStepThreeRequest: RestorePasswordStepThreeRequest) {
        _restorePasswordStepThreeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response =
                    repository.authRepository.restorePasswordStepThree(restorePasswordStepThreeRequest)
                _restorePasswordStepThreeResponse.value = handleResponse(response)
            } catch (e: Exception) {
                _restorePasswordStepThreeResponse.value =
                    NetworkResult.Error("Что то пошло не так :(")
            }
        } else {
            _restorePasswordStepThreeResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }


    private fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        return when {

            response.isSuccessful -> {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            }

            response.code() == 400 -> {
                NetworkResult.Error(response.message().toString())
            }

            response.code() == 401 -> {
                NetworkResult.Error(response.message())
            }
            response.code() == 403 -> {
                NetworkResult.Error("Вы ввели не правильную почту")
            }

            response.code() == 404 -> {
                NetworkResult.Error(response.message())
            }

            else -> {
                NetworkResult.Error(response.message())
            }

        }
    }

}