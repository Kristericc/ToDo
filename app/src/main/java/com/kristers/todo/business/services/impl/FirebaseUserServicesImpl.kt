package com.kristers.todo.business.services.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kristers.todo.business.repo.callbacks.FetchDataCallback
import com.kristers.todo.business.repo.callbacks.TodoCallback
import com.kristers.todo.business.repo.callbacks.UserCallback
import com.kristers.todo.business.repo.model.TodoDao
import com.kristers.todo.business.services.FirebaseUserServices
import com.kristers.todo.objects.Todo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseUserServicesImpl : FirebaseUserServices {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    override suspend fun createUser(email: String, password: String, callBack: UserCallback) {
        println("register user")
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            it.addOnFailureListener() {
                println(it)
            }
            callBack.onCallback(mAuth.currentUser)
        }
    }

    override suspend fun getLoggedUser(): FirebaseUser? {
        return mAuth!!.currentUser
    }

    override suspend fun loginUser(email: String, password: String, callBack: UserCallback) {
        println("login User")
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            callBack.onCallback(mAuth.currentUser)
        }
    }

    override suspend fun fetchAllTodo(callBack: FetchDataCallback, todoDao: TodoDao) {
        val todoList = mutableListOf<Todo>()
        db.collection(mAuth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                for (document in documentSnapshot) {
                    todoList.add(document.toObject<Todo>())
                }
                println("dabūja visus todo")
                GlobalScope.launch {
                    for (i in todoList.indices) {
                        todoDao.saveTodo(todoList[i])
                    }
                    callBack.onCallback()
                }
            }
            .addOnFailureListener { exception ->
            }
    }
}
