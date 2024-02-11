package com.sa.githubers.di

import com.sa.githubers.ui.mapper.UserDetailsDomainUiMapper
import com.sa.githubers.ui.mapper.UserRepoDomainUiMapper
import com.sa.githubers.ui.mapper.UsersDomainUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UiModule {
    @Provides
    fun providesUserListDomainUiMapper(): UsersDomainUiMapper {
        return UsersDomainUiMapper()
    }

    @Provides
    fun providesUserDetailsDomainUiMapper(): UserDetailsDomainUiMapper {
        return UserDetailsDomainUiMapper()
    }

    @Provides
    fun providesUserRepoDomainUiMapper(): UserRepoDomainUiMapper {
        return UserRepoDomainUiMapper()
    }

}