package com.experiment.android.templateapp.data.repo

import com.experiment.android.templateapp.data.remote.ApiService
import com.experiment.android.templateapp.models.RequestModel
import com.experiment.android.templateapp.models.ResponseData
import com.experiment.android.templateapp.utils.coroutines.CoroutinesDispatcherProvider
import com.experiment.android.templateapp.utils.coroutines.ResultHandler
import com.experiment.android.templateapp.utils.coroutines.runIO
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
) : UseCase {

    override suspend fun getData(from: String, to: String): ResultHandler<ResponseData> {
        return runIO(coroutinesDispatcherProvider) {
            apiService.getConvertedData(
                RequestModel(
                    from, to
                )
            )
        }
    }
}
