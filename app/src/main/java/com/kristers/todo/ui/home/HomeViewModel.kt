package com.kristers.todo.ui.home

import androidx.lifecycle.*
import com.kristers.todo.business.repo.TodoRepository
import com.kristers.todo.objects.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    var todoList = MutableLiveData<List<Todo>>()

    fun getAllPendingTodo() {
        viewModelScope.launch {
            todoList.value = todoRepository.getAllPendingTodo()
        }
    }
}
