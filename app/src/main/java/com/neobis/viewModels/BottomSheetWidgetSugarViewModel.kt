package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.widgetModels.SugarRequest
import com.neobis.models.widgetModels.SugarResponse
import com.neobis.models.widgetModels.UserSugar
import com.neobis.models.widgetModels.UserSugarResponse
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
class BottomSheetWidgetSugarViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    private var _sugarResponse: MutableLiveData<NetworkResult<SugarResponse>> = MutableLiveData()
    val sugarResponse: LiveData<NetworkResult<SugarResponse>> get() = _sugarResponse

    private var _trackSugarResponse: MutableLiveData<NetworkResult<UserSugarResponse>> =
        MutableLiveData()
    val trackSugarResponse: LiveData<NetworkResult<UserSugarResponse>> get() = _trackSugarResponse

    fun trackSugar(userSugar: UserSugar) {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallTrackSugar(userSugar)
        }
    }


    private suspend fun safeCallTrackSugar(userSugar: UserSugar) {
        _trackSugarResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.sugarTrack(userSugar)
                _trackSugarResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                _trackSugarResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _trackSugarResponse.postValue(
                NetworkResult.Error(
                    getApplication<Application>().resources.getString(
                        R.string.no_internet_connection
                    )
                )
            )
        }
    }

    fun setSugarValue(sugarRequest: SugarRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            setValue(sugarRequest)
        }
    }


    private suspend fun setValue(sugarRequest: SugarRequest) {
        _sugarResponse.postValue(NetworkResult.Loading())
        if (hasInternetConnection()) {
            try {
                val response = repository.widgetRepository.setNormalSugarValue(sugarRequest)
                _sugarResponse.postValue(handleResponse(response))
            } catch (e: Exception) {
                _sugarResponse.postValue(
                    NetworkResult.Error(
                        getApplication<Application>().resources.getString(
                            R.string.error_message
                        )
                    )
                )
            }
        } else {
            _sugarResponse.postValue(
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