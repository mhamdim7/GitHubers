package com.sa.githubers.di

import com.sa.githubers.data.datasource.DataSource
import com.sa.githubers.domain.usecases.UserDetailUseCase
import com.sa.githubers.domain.usecases.UserListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun providesUserListUseCase(dataSource: DataSource): UserListUseCase {
        return UserListUseCase(dataSource)
    }

    @Provides
    fun providesUserDetailUseCase(dataSource: DataSource): UserDetailUseCase {
        return UserDetailUseCase(dataSource)
    }

}