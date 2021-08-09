package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.user.UserResponse
import com.neobis.repository.Repository
import com.neobis.utils.Functions
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    private var _userResponse: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()
    val userResponse: LiveData<NetworkResult<UserResponse>> get() = _userResponse







    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            safeCallGetCurrentUser()
        }
    }



    private suspend fun safeCallGetCurrentUser() {
        _userResponse.postValue(NetworkResult.Loading())
        if (Functions.hasInternetConnection()) {
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