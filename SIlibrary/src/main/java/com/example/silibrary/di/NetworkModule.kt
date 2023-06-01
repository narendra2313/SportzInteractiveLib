package com.example.silibrary.di

import com.example.silibrary.BuildConfig
import com.example.silibrary.utils.CurlLoggingInterceptor
import com.example.silibrary.utils.CustomRequestInterceptor
import com.example.silibrary.utils.CustomValues
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Singleton
    @Provides
    fun providesCurlInterceptor(): CurlLoggingInterceptor {
        return CurlLoggingInterceptor("cURL")
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        curlLoggingInterceptor: CurlLoggingInterceptor,
        customRequestInterceptor: CustomRequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS).addInterceptor(customRequestInterceptor).apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(httpLoggingInterceptor)
                    addInterceptor(curlLoggingInterceptor)
                    addNetworkInterceptor(StethoInterceptor())
                }
            }.build()
    }


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        customValues: CustomValues
    ): Retrofit = Retrofit.Builder().baseUrl(customValues.baseUrl)
        .addConverterFactory(gsonConverterFactory).client(okHttpClient).build()

}