package com.experiment.android.templateapp.utils.network

import android.app.Application
import java.io.File
import javax.inject.Inject

class NetworkConfig @Inject constructor(private var application: Application) {
    /**
     * API Url
     */
    fun getBaseUrl(): String = "https://igfcc7aebk.execute-api.sa-east-1.amazonaws.com/default/"

    fun getCacheDir(): File = application.cacheDir

    fun getCacheSize(): Long = 10 * 1024 * 1024

    fun getTimeoutSeconds(): Long = 20
}