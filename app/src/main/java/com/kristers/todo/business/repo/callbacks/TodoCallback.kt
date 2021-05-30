package com.kristers.todo.business.repo.callbacks

import com.kristers.todo.objects.Todo

interface TodoCallback {
    suspend fun onCallback(todoList: List<Todo>)
}
