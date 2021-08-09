package com.neobis.network

import com.neobis.models.auth.*
import com.neobis.utils.Constants.LOGIN
import com.neobis.utils.Constants.REGISTRATION_STEP_1
import com.neobis.utils.Constants.REGISTRATION_STEP_2
import com.neobis.utils.Constants.REGISTRATION_STEP_3
import com.neobis.utils.Constants.RESTORE_PASSWORD_STEP_1
import com.neobis.utils.Constants.RESTORE_PASSWORD_STEP_2
import com.neobis.utils.Constants.RESTORE_PASSWORD_STEP_3
import okhttp3.ResponseBody


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthorizationApi {

    @POST(REGISTRATION_STEP_1)
    suspend fun registerStepOne(@Body registerStepOneRequest: RegisterStepOneRequest): Response<RegisterStepOneResponse>

    @POST(REGISTRATION_STEP_2)
    suspend fun registerStepTwo(@Body registerStepTwoRequest: RegisterStepTwoRequest): Response<RegisterStepTwoResponse>

    @POST(REGISTRATION_STEP_3)
    suspend fun registerStepThree(@Body registerStep3Request: RegisterStepThreeRequest): Response<RegisterStepThreeResponse>


    @POST(LOGIN)
    suspend fun userLogin(@Body userLoginRequest: UserLoginRequest): Response<UserLoginResponse>

    @POST(RESTORE_PASSWORD_STEP_1)
    suspend fun restorePasswordStepOne(@Body restorePasswordStepOneRequest: RestorePasswordStepOneRequest): Response<RestorePasswordStepOneResponse>

    @POST(RESTORE_PASSWORD_STEP_2)
    suspend fun restorePasswordStepTwo(@Body restorePasswordStepTwoRequest: RestorePasswordStepTwoRequest): Response<RestorePasswordStepTwoResponse>

    @POST(RESTORE_PASSWORD_STEP_3)
    suspend fun restorePasswordStepThree(@Body restorePasswordStepThreeRequest: RestorePasswordStepThreeRequest): Response<RestorePasswordStepThreeResponse>


}
