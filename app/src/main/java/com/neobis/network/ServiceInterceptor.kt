package com.neobis.network

import com.neobis.utils.MyApplication
import com.neobis.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ServiceInterceptor : Interceptor {


    // https://stackoverflow.com/questions/43051558/dagger-retrofit-adding-auth-headers-at-runtime


    private lateinit var request: Request

    private val sessionManager: SessionManager by lazy { SessionManager(MyApplication.getContext()!!) }

    override fun intercept(chain: Interceptor.Chain): Response {
        request = chain.request()
        if (sessionManager.fetchUserToken() != null) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer ${sessionManager.fetchUserToken()}")
                .build()
        }

        return chain.proceed(request)

    }
}