package com.kristers.todo.business.repo.callbacks

import com.google.firebase.auth.FirebaseUser
interface UserCallback {
    fun onCallback(user: FirebaseUser?)
}
