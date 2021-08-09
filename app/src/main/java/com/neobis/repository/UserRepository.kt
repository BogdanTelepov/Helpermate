package com.neobis.repository

import com.neobis.models.user.UserResponse
import com.neobis.network.UserApi
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    suspend fun getCurrentUser(): Response<UserResponse> {
        return userApi.getCurrentUser()
    }
}