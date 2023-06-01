package com.example.silibrary.utils

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This class will append auth key in each request
 */
@Singleton
class CustomRequestInterceptor @Inject constructor(
    val customValues: CustomValues
    ) : Interceptor {

    @Throws(IllegalArgumentException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("auth", customValues.apiAuthKey)
        val token =   customValues.userToken
        token?.let {
            builder.addHeader("usertoken", it)
        }
        return chain.proceed(builder.build())
    }
}
