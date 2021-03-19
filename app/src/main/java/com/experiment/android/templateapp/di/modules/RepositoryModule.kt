package com.experiment.android.templateapp.di.modules

import com.experiment.android.templateapp.data.remote.ApiService
import com.experiment.android.templateapp.data.repo.DataRepository
import com.experiment.android.templateapp.utils.coroutines.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

/*@Module
@Singleton
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    @Singleton
    fun providesDataRepository(
        apiService: ApiService,
        coroutinesDispatcherProvider: CoroutinesDispatcherProvider
    ): DataRepository {
        return DataRepository(apiService, coroutinesDispatcherProvider)
    }
}*/
