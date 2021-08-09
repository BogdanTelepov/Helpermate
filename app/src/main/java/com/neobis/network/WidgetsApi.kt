package com.neobis.network

import com.neobis.models.history.SleepHistoryResponse
import com.neobis.models.widgetModels.*
import com.neobis.utils.Constants.GET_ALL_WIDGETS
import com.neobis.utils.Constants.NEW_MEDICATIONS
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WidgetsApi {


    @GET(GET_ALL_WIDGETS)
    suspend fun getAllWidgets(): Response<List<WidgetModel>>


    @POST(NEW_MEDICATIONS)
    suspend fun addNewMedications(@Body medicationsRequest: MedicationsRequest): Response<MedicationsResponse>

    @POST("/widgets/set-normal-sugar")
    suspend fun setNormalSugarValue(@Body sugarRequest: SugarRequest): Response<SugarResponse>

    @POST("/sleep-widget/track")
    suspend fun sleepTrack(@Body userSleep: UserSleep): Response<UserSleepResponse>

    @POST("/sugar-widget/track")
    suspend fun sugarTrack(@Body userSugar: UserSugar): Response<UserSugarResponse>

    @GET("/main-page")
    suspend fun getSleepValues(): Response<MainPageGetSleepValues>

    @GET("/main-page")
    suspend fun getSugarValues(): Response<MainPageGetSugarValues>

    @GET("/sleep-widget/history")
    suspend fun getSleepHistory(): Response<SleepHistoryResponse>

    @GET("/sugar-widget/history")
    suspend fun getSugarHistory(): Response<List<UserSugarResponse>>

}