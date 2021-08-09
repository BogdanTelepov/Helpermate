package com.neobis.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neobis.R
import com.neobis.models.widgetModels.MedicationsRequest
import com.neobis.models.widgetModels.MedicationsResponse
import com.neobis.models.widgetModels.PharmacyModel
import com.neobis.repository.WidgetsRepository
import com.neobis.utils.Functions.Companion.hasInternetConnection
import com.neobis.utils.NetworkResult
import com.neobis.utils.notifyObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BottomSheetWidgetPharmacyViewModel @Inject constructor(
    private val widgetsRepository: WidgetsRepository,
    application: Application
) : AndroidViewModel(application) {


    private var _medicationsResponse: MutableLiveData<NetworkResult<MedicationsResponse>> =
        MutableLiveData()
    val medicationsResponse: LiveData<NetworkResult<MedicationsResponse>> get() = _medicationsResponse


    private var _listOfPharmacy: MutableLiveData<MutableList<PharmacyModel>> = MutableLiveData()
    val listOfPharmacy: LiveData<MutableList<PharmacyModel>> get() = _listOfPharmacy

    init {
        _listOfPharmacy.value = ArrayList()
    }

    fun createListPharmacy(pharmacyModel: PharmacyModel) {
        _listOfPharmacy.value?.add(pharmacyModel)
        _listOfPharmacy.notifyObserver()
    }

    fun deleteItemFromList(pharmacyModel: PharmacyModel) {
        _listOfPharmacy.value?.remove(pharmacyModel)
        _listOfPharmacy.notifyObserver()
    }


    fun addMedications(medicationsRequest: MedicationsRequest) {
        viewModelScope.launch {
            addNewMedications(medicationsRequest)
        }
    }


    private suspend fun addNewMedications(medicationsRequest: MedicationsRequest) {
        _medicationsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {

                val response = widgetsRepository.addNewMedications(medicationsRequest)
                _medicationsResponse.value = handleResponse(response)

            } catch (e: Exception) {
                _medicationsResponse.value =
                    NetworkResult.Error(getApplication<Application>().resources.getString(R.string.error_message))
            }
        } else {
            _medicationsResponse.value = NetworkResult.Error(
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