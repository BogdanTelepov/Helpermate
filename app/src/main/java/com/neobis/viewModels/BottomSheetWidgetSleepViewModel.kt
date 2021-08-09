package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.widgetModels.UserSleep
import com.neobis.models.widgetModels.UserSleepResponse
import com.neobis.repository.Repository
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject
import com.neobis.utils.Functions.Companion.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@HiltViewModel
class BottomSheetWidgetSleepViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    private var _userSleepResponse: MutableLiveData<NetworkResult<UserSleepResponse>> =
        MutableLiveData()
    val userSleepResponse: LiveData<NetworkResult<UserSleepResponse>> get() = _userSleepResponse

    fun trackSleep(userSleep: UserSleep) {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallTrackSleep(userSleep)
        }
    }


    private suspend fun safeCallTrackSleep(userSleep: UserSleep) {
        _userSleepResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.sleepTrack(userSleep)
                _userSleepResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                _userSleepResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _userSleepResponse.postValue(
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