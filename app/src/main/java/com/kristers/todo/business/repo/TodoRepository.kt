package com.kristers.todo.business.repo

import com.kristers.todo.business.repo.callbacks.TodoCallback
import com.kristers.todo.business.repo.model.TodoDao
import com.kristers.todo.business.services.FirebaseTodoServices
import com.kristers.todo.objects.Todo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val fService: FirebaseTodoServices,
    private val todoDao: TodoDao
) {
    suspend fun saveTodo(todo: Todo) {
        todoDao.saveTodo(todo)
        fService.saveTodo(todo)
    }

    suspend fun getAllPendingTodo(): List<Todo> {
        refreshAllPendingTodo()

        return todoDao.loadAllTodoSort(false)
    }

    suspend fun refreshAllPendingTodo() {
        val todoExists = todoDao.hasTodo(currentTimeMillis() - FRESH_TIMEOUT)
        if (todoExists != null) {
            fService.getAllPendingTodo(
                object : TodoCallback {
                    override suspend fun onCallback(todoList: List<Todo>) {
                        for (i in todoList.indices) {
                            todoDao.saveTodo(todoList[i])
                        }
                    }
                }
            )
        }
    }

    suspend fun getAllCompletedTodo(): List<Todo> {
        refreshAllCompletedTodo()
        return todoDao.loadAllTodoSort(true)
    }

    suspend fun refreshAllCompletedTodo() {
        val todoExists = todoDao.hasTodo(currentTimeMillis() - FRESH_TIMEOUT)
        if (todoExists != null) {
            fService.getAllCompletedTodo(
                object : TodoCallback {
                    override suspend fun onCallback(todoList: List<Todo>) {
                        for (i in todoList.indices) {
                            todoDao.saveTodo(todoList[i])
                        }
                    }
                }
            )
        }
    }

    suspend fun completeTodo(todoId: String) {
        todoDao.updateTodo(true, todoId)
        fService.completeTodo(todoId)
    }

    suspend fun deleteTodo(todoId: String) {
        GlobalScope.launch {
            val todo = todoDao.getTodo(todoId)
            todoDao.delete(todo)
        }
        fService.deleteTodo(todoId)
    }

    companion object {
        val FRESH_TIMEOUT = TimeUnit.DAYS.toHours(1)
    }
}
