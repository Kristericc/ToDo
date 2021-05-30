package com.kristers.todo.di

import com.kristers.todo.business.services.FirebaseTodoServices
import com.kristers.todo.business.services.FirebaseUserServices
import com.kristers.todo.business.services.impl.FirebaseTodoServicesImpl
import com.kristers.todo.business.services.impl.FirebaseUserServicesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideFirebaseUserService(): FirebaseUserServices {
        return FirebaseUserServicesImpl()
    }

    @Singleton
    @Provides
    fun provideFirebaseTodoService(): FirebaseTodoServices {
        return FirebaseTodoServicesImpl()
    }
}
