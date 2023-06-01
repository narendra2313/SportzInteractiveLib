package com.example.silibrary.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This class will append auth key in each request
 */
@Singleton
class CustomRequestInterceptor @Inject constructor(
    private val customValues: CustomValues
) : Interceptor {

    @Throws(IllegalArgumentException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        customValues.apiAuthKey?.let { builder.addHeader("auth", it) }
        customValues.userToken?.let {
            builder.addHeader("usertoken", it)
        }
        return chain.proceed(builder.build())
    }
}
