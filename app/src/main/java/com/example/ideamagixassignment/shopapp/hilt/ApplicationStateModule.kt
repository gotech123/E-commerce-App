package com.example.ideamagixassignment.shopapp.hilt

import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.ideamagixassignment.shopapp.redux.Store
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStateModule {

    @Provides
    @Singleton
    fun providesApplicationStateStore(): Store<ApplicationState> {
        return Store(ApplicationState())
    }
}