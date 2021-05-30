package com.kristers.todo.business.services

import com.kristers.todo.business.repo.callbacks.TodoCallback
import com.kristers.todo.objects.Todo

interface FirebaseTodoServices {

    suspend fun saveTodo(todo: Todo)
    suspend fun getAllPendingTodo(callBack: TodoCallback)
    suspend fun getAllCompletedTodo(callBack: TodoCallback)
    suspend fun completeTodo(todoId: String)
    suspend fun deleteTodo(todoId: String)
}
