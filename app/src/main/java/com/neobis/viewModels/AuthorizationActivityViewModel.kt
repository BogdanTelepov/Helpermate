package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.models.auth.UserLoginRequest
import com.neobis.models.auth.UserLoginResponse
import com.neobis.repository.Repository
import com.neobis.utils.Functions.Companion.hasInternetConnection
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

import javax.inject.Inject

@HiltViewModel
class AuthorizationActivityViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) :
    AndroidViewModel(application) {


    private var _loginResponse: MutableLiveData<NetworkResult<UserLoginResponse>> =
        MutableLiveData()
    val loginResponse: LiveData<NetworkResult<UserLoginResponse>> get() = _loginResponse

    fun login(userLoginRequest: UserLoginRequest) {
        viewModelScope.launch {
            userLogin(userLoginRequest)
        }
    }


    private suspend fun userLogin(userLoginRequest: UserLoginRequest) {
        _loginResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.authRepository.userLogin(userLoginRequest)
                _loginResponse.value = handleLoginResponse(response)
            } catch (e: Exception) {
                _loginResponse.value = NetworkResult.Error("Что то пошло не так :(")
            }
        } else {
            _loginResponse.value = NetworkResult.Error("Нет интернет соединения")
        }
    }


    private fun handleLoginResponse(response: Response<UserLoginResponse>): NetworkResult<UserLoginResponse> {
        return when {

            response.isSuccessful -> {
                val responseBody = response.body()
                NetworkResult.Success(responseBody!!)
            }

            response.code() == 401 -> {
                NetworkResult.Error(response.message())
            }
            response.code() == 403 -> {
                NetworkResult.Error("Вы ввели не правильный логин или пароль")
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