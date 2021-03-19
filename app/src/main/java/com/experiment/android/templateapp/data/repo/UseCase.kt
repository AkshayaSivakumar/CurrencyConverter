package com.experiment.android.templateapp.data.repo

import com.experiment.android.templateapp.models.ResponseData
import com.experiment.android.templateapp.utils.coroutines.ResultHandler

interface UseCase {
    suspend fun getData(from: String, to: String): ResultHandler<ResponseData>
}