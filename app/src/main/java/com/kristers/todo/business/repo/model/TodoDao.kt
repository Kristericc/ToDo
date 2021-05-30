package com.kristers.todo.business.repo.model

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.kristers.todo.objects.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo WHERE id = :todoId")
    suspend fun getTodo(todoId: String): Todo

    @Query("SELECT * FROM todo WHERE lastUpdated >= :difference")
    suspend fun hasTodo(difference: Long): Todo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE done = :bool")
    suspend fun loadAllTodoSort(bool: Boolean): List<Todo>

    @Query("UPDATE todo SET done = :done WHERE id = :todoId")
    suspend fun updateTodo(done: Boolean, todoId: String)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("DELETE FROM todo")
    suspend fun nukeTable()
}
