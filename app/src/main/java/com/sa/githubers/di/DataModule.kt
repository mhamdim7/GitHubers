package com.sa.githubers.di

import android.content.Context
import com.sa.githubers.data.NetworkInterceptor
import com.sa.githubers.data.api.ApiService
import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.data.datasource.DataSourceImpl
import com.sa.githubers.data.mapper.UserDetailsDataDomainMapper
import com.sa.githubers.data.mapper.UserListDataDomainMapper
import com.sa.githubers.data.mapper.UserRepoDataDomainMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesRetrofit(@ApplicationContext appContext: Context): Retrofit {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val httpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(appContext))
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesDataSource(
        apiService: ApiService,
        userListMapper: UserListDataDomainMapper,
        userDetailsMapper: UserDetailsDataDomainMapper,
        userRepoMapper: UserRepoDataDomainMapper
    ): DataSource {
        return DataSourceImpl(apiService, userListMapper, userDetailsMapper, userRepoMapper)
    }

    // Data to Domain mappers
    @Provides
    fun providesUserListDataDomainMapper(): UserListDataDomainMapper {
        return UserListDataDomainMapper()
    }

    @Provides
    fun providesUserDetailsDataDomainMapper(): UserDetailsDataDomainMapper {
        return UserDetailsDataDomainMapper()
    }

    @Provides
    fun providesUserRepoDataDomainMapper(): UserRepoDataDomainMapper {
        return UserRepoDataDomainMapper()
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }

}