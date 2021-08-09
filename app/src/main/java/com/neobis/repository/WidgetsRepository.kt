package com.neobis.repository

import com.neobis.models.history.SleepHistoryResponse
import com.neobis.models.widgetModels.*
import com.neobis.network.WidgetsApi
import retrofit2.Response
import javax.inject.Inject

class WidgetsRepository @Inject constructor(private val widgetsApi: WidgetsApi) {


    suspend fun getAllWidgets(): Response<List<WidgetModel>> {
        return widgetsApi.getAllWidgets()
    }

    suspend fun addNewMedications(medicationsRequest: MedicationsRequest): Response<MedicationsResponse> {
        return widgetsApi.addNewMedications(medicationsRequest)
    }

    suspend fun setNormalSugarValue(sugarRequest: SugarRequest): Response<SugarResponse> {
        return widgetsApi.setNormalSugarValue(sugarRequest)
    }

    suspend fun sleepTrack(userSleep: UserSleep): Response<UserSleepResponse> {
        return widgetsApi.sleepTrack(userSleep)
    }

    suspend fun sugarTrack(userSugar: UserSugar): Response<UserSugarResponse> {
        return widgetsApi.sugarTrack(userSugar)
    }

    suspend fun getSleepValues(): Response<MainPageGetSleepValues> {
        return widgetsApi.getSleepValues()
    }

    suspend fun getSugarValues(): Response<MainPageGetSugarValues> {
        return widgetsApi.getSugarValues()
    }

    suspend fun getHistorySleep(): Response<SleepHistoryResponse> {
        return widgetsApi.getSleepHistory()
    }


    suspend fun getHistorySugar(): Response<List<UserSugarResponse>> {
        return widgetsApi.getSugarHistory()
    }


}