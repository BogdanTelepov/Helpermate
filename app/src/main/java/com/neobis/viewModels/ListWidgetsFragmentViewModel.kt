package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.widgetModels.WidgetModel
import com.neobis.repository.WidgetsRepository
import com.neobis.utils.Functions.Companion.hasInternetConnection
import com.neobis.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ListWidgetsFragmentViewModel @Inject constructor(
    private val widgetsRepository: WidgetsRepository,
    application: Application
) : AndroidViewModel(application) {


    private var _responseWidgetListItem: MutableLiveData<NetworkResult<List<WidgetModel>>> =
        MutableLiveData()
    val responseWidgetListItem: LiveData<NetworkResult<List<WidgetModel>>> get() = _responseWidgetListItem


    fun getListWidgets() {
        viewModelScope.launch {
            getAllWidgets()
        }
    }


    private suspend fun getAllWidgets() {
        _responseWidgetListItem.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = widgetsRepository.getAllWidgets()
                _responseWidgetListItem.value = handleResponse(response)
            } catch (e: Exception) {
                _responseWidgetListItem.value =
                    NetworkResult.Error(getApplication<Application>().resources.getString(R.string.error_message))
            }
        } else {
            _responseWidgetListItem.value = NetworkResult.Error(
                getApplication<Application>().resources.getString(
                    R.string.no_internet_connection
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