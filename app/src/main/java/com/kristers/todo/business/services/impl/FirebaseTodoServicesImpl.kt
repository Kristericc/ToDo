package com.kristers.todo.business.services.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.kristers.todo.business.repo.callbacks.TodoCallback
import com.kristers.todo.business.services.FirebaseTodoServices
import com.kristers.todo.objects.Todo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseTodoServicesImpl : FirebaseTodoServices {
    private val db = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    override suspend fun saveTodo(todo: Todo) {
        db.collection(mAuth.currentUser!!.uid).document(todo.id).set(todo)
    }

    override suspend fun getAllPendingTodo(callBack: TodoCallback) {
        val todoList = mutableListOf<Todo>()
        db.collection(mAuth.currentUser!!.uid)
            .whereEqualTo("done", false)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                for (document in documentSnapshot) {
                    todoList.add(document.toObject<Todo>())
                }
                GlobalScope.launch { callBack.onCallback(todoList) }
            }
            .addOnFailureListener { exception ->
            }
    }

    override suspend fun getAllCompletedTodo(callBack: TodoCallback) {
        val todoList = mutableListOf<Todo>()
        db.collection(mAuth.currentUser!!.uid)
            .whereEqualTo("done", true)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                for (document in documentSnapshot) {
                    todoList.add(document.toObject<Todo>())
                }
                GlobalScope.launch { callBack.onCallback(todoList) }
            }
            .addOnFailureListener { exception ->
            }
    }

    override suspend fun completeTodo(todoId: String) {
        db.collection(mAuth.currentUser!!.uid).document(todoId)
            .update("done", true)
    }

    override suspend fun deleteTodo(todoId: String) {
        db.collection(mAuth.currentUser!!.uid).document(todoId)
            .delete()
    }
}
