package com.backbase.assignment.core.data

import com.backbase.assignment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Mohamed Hashim on 15/11/2021.
 */

class RequestInterceptor : Interceptor {

    /**
     *  build api url and interceptor request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}
