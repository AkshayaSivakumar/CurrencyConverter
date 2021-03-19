package com.experiment.android.templateapp.data.remote

import com.experiment.android.templateapp.models.RequestModel
import com.experiment.android.templateapp.models.ResponseData
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("money-conversion-interview-exercise-api")
    suspend fun getConvertedData(@Body request: RequestModel): ResponseData
}