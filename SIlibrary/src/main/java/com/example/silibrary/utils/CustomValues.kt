package com.example.silibrary.utils

import javax.inject.Singleton

@Singleton
data class CustomValues(
    val baseUrl: String?, val apiAuthKey: String?, val userToken: String?,
    val isDebug: Boolean = true
)