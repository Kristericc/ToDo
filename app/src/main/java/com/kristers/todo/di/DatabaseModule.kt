package com.kristers.todo.di

import com.kristers.todo.CustomApplication
import com.kristers.todo.business.repo.model.TodoDao
import com.kristers.todo.db.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideStudentDao(context: CustomApplication): TodoDao {
        return TodoDatabase.getInstance(context).todoDao()
    }
}
