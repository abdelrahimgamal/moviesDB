package com.softxpert.moviesdb.data.api.interceptors

import com.softxpert.moviesdb.utils.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Logger.i(message)
    }
}
