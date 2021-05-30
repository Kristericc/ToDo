package com.kristers.todo.objects

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Todo @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val date: Long = 0L,
    var importance: String = "",
    val description: String = "",
    val done: Boolean = false,
    val lastUpdated: Long = 0L
)
