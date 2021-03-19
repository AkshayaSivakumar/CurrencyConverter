package com.experiment.android.templateapp.di.modules

import android.app.Application
import com.experiment.android.templateapp.BuildConfig
import com.experiment.android.templateapp.data.remote.ApiService
import com.experiment.android.templateapp.di.scope.InternalApi
import com.experiment.android.templateapp.utils.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @InternalApi
    @Provides
    fun providesNetworkConfig(context: Application): NetworkConfig {
        return NetworkConfig(context)
    }

    @InternalApi
    @Provides
    @Singleton
    fun providesOkHttpCache(
        @InternalApi
        networkConfig: NetworkConfig
    ): Cache {
        return Cache(networkConfig.getCacheDir(), networkConfig.getCacheSize())
    }

    @InternalApi
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG)
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        else HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }

    @InternalApi
    @Provides
    @Singleton
    fun providesOkHttpClient(
        @InternalApi
        networkConfig: NetworkConfig,
        @InternalApi
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @InternalApi
        cache: Cache
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(networkConfig.getTimeoutSeconds(), TimeUnit.SECONDS)
            .connectTimeout(networkConfig.getTimeoutSeconds(), TimeUnit.SECONDS)
            .cache(cache)

        return builder.build();
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        @InternalApi
        networkConfig: NetworkConfig,
        @InternalApi
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(networkConfig.getBaseUrl())
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}