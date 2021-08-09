package com.neobis.network

import com.neobis.models.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {



    @GET("/users/current")
    suspend fun getCurrentUser():Response<UserResponse>
}