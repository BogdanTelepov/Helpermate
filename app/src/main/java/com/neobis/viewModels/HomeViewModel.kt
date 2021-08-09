package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.user.UserResponse
import com.neobis.models.widgetModels.MainPageGetSleepValues
import com.neobis.models.widgetModels.MainPageGetSugarValues
import com.neobis.repository.Repository

import com.neobis.utils.Functions.Companion.hasInternetConnection
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private var _userResponse: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()
    val userResponse: LiveData<NetworkResult<UserResponse>> get() = _userResponse

    private var _getSleepValuesResponse: MutableLiveData<NetworkResult<MainPageGetSleepValues>> =
        MutableLiveData()
    val getSleepValuesResponse: LiveData<NetworkResult<MainPageGetSleepValues>> get() = _getSleepValuesResponse

    private var _getSugarValuesResponse: MutableLiveData<NetworkResult<MainPageGetSugarValues>> =
        MutableLiveData()
    val getSugarValuesResponse: LiveData<NetworkResult<MainPageGetSugarValues>> get() = _getSugarValuesResponse


    fun getSugarValues() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallGetSugarResponse()
        }
    }

    private suspend fun safeCallGetSugarResponse() {
        _getSugarValuesResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.getSugarValues()
                _getSugarValuesResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                _getSugarValuesResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _getSugarValuesResponse.postValue(
                NetworkResult.Error(
                    getApplication<Application>().resources.getString(
                        R.string.no_internet_connection
                    )
                )
            )
        }
    }


    fun getSleepValues() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallGetSleepValuesResponse()
        }
    }

    private suspend fun safeCallGetSleepValuesResponse() {
        _getSleepValuesResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.getSleepValues()
                _getSleepValuesResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                _getSleepValuesResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _getSleepValuesResponse.postValue(
                NetworkResult.Error(
                    getApplication<Application>().resources.getString(
                        R.string.no_internet_connection
                    )
                )
            )
        }
    }


    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallGetCurrentUser()
        }
    }

    private suspend fun safeCallGetCurrentUser() {
        _userResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.userRep.getCurrentUser()
                _userResponse.postValue(handleResponse(response))

            } catch (e: Exception) {
                _userResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _userResponse.postValue(
                NetworkResult.Error(
                    getApplication<Application>().resources.getString(
                        R.string.no_internet_connection
                    )
                )
            )
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
                NetworkResult.Error(response.message().toString())
            }

            response.code() == 403 -> {
                NetworkResult.Error(response.message().toString())
            }

            else -> {
                NetworkResult.Error(response.message().toString())
            }
        }
    }
}