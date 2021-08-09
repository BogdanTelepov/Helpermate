package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.history.SleepHistoryResponse
import com.neobis.models.widgetModels.SugarRequest
import com.neobis.models.widgetModels.SugarResponse
import com.neobis.models.widgetModels.UserSugarResponse
import com.neobis.repository.Repository
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject
import com.neobis.utils.Functions.Companion.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.lang.Exception

@HiltViewModel
class HistoryFragmentViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    private var _historySleepResponse: MutableLiveData<NetworkResult<SleepHistoryResponse>> =
        MutableLiveData()
    val historySleepResponse: LiveData<NetworkResult<SleepHistoryResponse>> get() = _historySleepResponse

    private var _historySugarResponse: MutableLiveData<NetworkResult<List<UserSugarResponse>>> =
        MutableLiveData()
    val historySugarResponse: LiveData<NetworkResult<List<UserSugarResponse>>> get() = _historySugarResponse


    fun getSugarHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallSugarResponse()
        }
    }

    private suspend fun safeCallSugarResponse() {
        _historySugarResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.getHistorySugar()
                _historySugarResponse.postValue(handleResponse(response))

            } catch (e: Exception) {
                _historySugarResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _historySugarResponse.postValue(
                NetworkResult.Error(
                    getApplication<Application>().resources.getString(
                        R.string.no_internet_connection
                    )
                )
            )
        }
    }


    fun getSleepHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallSleepResponse()
        }
    }


    private suspend fun safeCallSleepResponse() {
        _historySleepResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.getHistorySleep()
                _historySleepResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                _historySleepResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _historySleepResponse.postValue(
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