package com.kristers.todo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.kristers.todo.business.repo.UserRepository
import com.kristers.todo.business.repo.callbacks.FetchDataCallback
import com.kristers.todo.business.repo.callbacks.UserCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    var _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    var _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    fun getLoggedUser() {
        viewModelScope.launch {
            _user.value = userRepository.getLoggedIn()
        }
    }

    fun logInUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginUser(
                email, password,
                object : UserCallback {
                    override fun onCallback(user: FirebaseUser?) {
                        if (user != null) {
                            GlobalScope.launch {
                                userRepository.fetchFromFirebase(
                                    object : FetchDataCallback {
                                        override suspend fun onCallback() {
                                            withContext(Dispatchers.Main) {
                                                _user.value = user!!
                                            }
                                        }
                                    }
                                )
                            }
                        } else {
                            registerUser(email, password)
                        }
                    }
                }
            )
        }
    }

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            userRepository.registerUser(
                email, password,
                object : UserCallback {
                    override fun onCallback(user: FirebaseUser?) {
                        if (user != null) {
                            GlobalScope.launch {
                                userRepository.fetchFromFirebase(
                                    object : FetchDataCallback {
                                        override suspend fun onCallback() {
                                            withContext(Dispatchers.Main) {
                                                _user.value = user!!
                                            }
                                        }
                                    }
                                )
                            }
                        }else{
                            _error.value = true
                        }
                    }
                }
            )
        }
    }

    fun clearTodoTable() {
        viewModelScope.launch {
            userRepository.clearTable()
        }
    }
}
