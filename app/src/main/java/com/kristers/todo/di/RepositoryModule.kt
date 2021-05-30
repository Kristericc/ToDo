package com.kristers.todo.di

import com.kristers.todo.business.repo.TodoRepository
import com.kristers.todo.business.repo.UserRepository
import com.kristers.todo.business.repo.model.TodoDao
import com.kristers.todo.business.services.FirebaseTodoServices
import com.kristers.todo.business.services.FirebaseUserServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(fService: FirebaseUserServices, todoDao: TodoDao): UserRepository {
        return UserRepository(
            fService,
            todoDao
        )
    }

    @Singleton
    @Provides
    fun provideTodoRepository(fService: FirebaseTodoServices, todoDao: TodoDao): TodoRepository {
        return TodoRepository(
            fService,
            todoDao
        )
    }
}
