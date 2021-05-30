package com.kristers.todo.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kristers.todo.business.repo.TodoRepository
import com.kristers.todo.objects.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    val _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo>
        get() = _todo

    fun completeTodo() {
        viewModelScope.launch {
            todoRepository.completeTodo(todo.value!!.id)
        }
    }

    fun deleteTodo() {
        viewModelScope.launch {
            todoRepository.deleteTodo(todo.value!!.id)
        }
    }
}
