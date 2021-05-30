package com.kristers.todo.ui.new_todo

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
class NewTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    val _todo = MutableLiveData<Todo>()
    val todo: LiveData<Todo>
        get() = _todo

    fun saveTodo() {
        viewModelScope.launch {
            todo.value?.let { todoRepository.saveTodo(it) }
        }
    }
}
