package com.kristers.todo.ui.recyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kristers.todo.R

class CardViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var todoTitle: TextView = itemView.findViewById(R.id.todo_title)
    var todoImportance: TextView = itemView.findViewById(R.id.todo_importance)
    var todoDate: TextView = itemView.findViewById(R.id.todo_date)
}
