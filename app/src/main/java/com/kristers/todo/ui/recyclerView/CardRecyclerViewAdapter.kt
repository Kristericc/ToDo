package com.kristers.todo.ui.recyclerView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kristers.todo.R
import com.kristers.todo.objects.Todo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*

class CardRecyclerViewAdapter(private var todoList: List<Todo>, val view: View) : RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.card_grid, parent, false)
        return CardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        if (position < todoList.size) {
            val todo = todoList[position]
            holder.todoTitle.text = todo.title
            holder.todoImportance.text = todo.importance
            holder.todoImportance.setTextColor(getColorResource(todo.importance))

            val date = Date(todo.date)
            val df2 = SimpleDateFormat("yyyy-MM-dd")
            val dateText: String = df2.format(date)

            holder.todoDate.text = dateText

            holder.itemView.setOnClickListener {
                cardClicked(todo)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun sortByDate() {
        todoList = todoList.sortedBy { it.date }
        notifyDataSetChanged()
    }

    fun sortByImportance() {
        for (i in todoList.indices) {
            if (todoList[i].importance == "High") todoList[i].importance = "0"
            if (todoList[i].importance == "Medium") todoList[i].importance = "1"
            if (todoList[i].importance == "Low") todoList[i].importance = "2"
        }
        todoList = todoList.sortedBy { it.importance }
        for (i in todoList.indices) {
            if (todoList[i].importance == "0") todoList[i].importance = "High"
            if (todoList[i].importance == "1") todoList[i].importance = "Medium"
            if (todoList[i].importance == "2") todoList[i].importance = "Low"
        }
        notifyDataSetChanged()
    }

    fun cardClicked(todo: Todo) {
        val bundle = bundleOf("todo" to Json.encodeToString(todo))
        view.findNavController().navigate(R.id.action_to_todo, bundle)
    }
    fun getColorResource(importance: String): Int {
        return when (importance) {
            "High" -> Color.parseColor("#FF5558")
            "Medium" -> Color.parseColor("#FFBA42")
            else -> {
                Color.parseColor("#3a95cb")
            }
        }
    }
}
