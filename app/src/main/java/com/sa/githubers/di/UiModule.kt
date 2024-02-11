package com.sa.githubers.di

import com.sa.githubers.presentation.mapper.UserDetailsDomainToUiMapper
import com.sa.githubers.presentation.mapper.UserRepoDomainToUiMapper
import com.sa.githubers.presentation.mapper.UserListDomainToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {
    @Provides
    fun providesUserListDomainUiMapper(): UserListDomainToUiMapper {
        return UserListDomainToUiMapper()
    }

    @Provides
    fun providesUserDetailsDomainUiMapper(): UserDetailsDomainToUiMapper {
        return UserDetailsDomainToUiMapper()
    }

    @Provides
    fun providesUserRepoDomainUiMapper(): UserRepoDomainToUiMapper {
        return UserRepoDomainToUiMapper()
    }

}