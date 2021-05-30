package com.kristers.todo.business.repo

import com.kristers.todo.business.repo.callbacks.FetchDataCallback
import com.kristers.todo.business.repo.callbacks.UserCallback
import com.kristers.todo.business.repo.model.TodoDao
import com.kristers.todo.business.services.FirebaseUserServices
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val fService: FirebaseUserServices,
    private val todoDao: TodoDao
) {

    suspend fun getLoggedIn() =
        fService.getLoggedUser()
    suspend fun registerUser(email: String, password: String, param: UserCallback) =
        fService.createUser(email, password, param)
    suspend fun loginUser(email: String, password: String, param: UserCallback) =
        fService.loginUser(email, password, param)

    suspend fun clearTable() {
        println("iztīrīja visus todo")
        todoDao.nukeTable()
    }

    suspend fun fetchFromFirebase(param: FetchDataCallback) {
        fService.fetchAllTodo(param, todoDao)
    }
}
