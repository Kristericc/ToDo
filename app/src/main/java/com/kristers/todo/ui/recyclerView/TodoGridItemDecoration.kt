package com.kristers.todo.ui.recyclerView

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class TodoGridItemDecoration(private val largePadding: Int, private val smallPadding: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outReact: Rect, itemPosition: Int, paren: RecyclerView) {
        outReact.left = smallPadding
        outReact.right = largePadding
    }
}
