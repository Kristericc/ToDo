package com.kristers.todo.business.services

import com.google.firebase.auth.FirebaseUser
import com.kristers.todo.business.repo.callbacks.FetchDataCallback
import com.kristers.todo.business.repo.callbacks.UserCallback
import com.kristers.todo.business.repo.model.TodoDao

interface FirebaseUserServices {
    suspend fun createUser(email: String, password: String, param: UserCallback)
    suspend fun getLoggedUser(): FirebaseUser?
    suspend fun loginUser(email: String, password: String, param: UserCallback)
    suspend fun fetchAllTodo(callback: FetchDataCallback, todoDao: TodoDao)
}
