package com.ahmad.ecampus.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ahmad.ecampus.R
import androidx.core.graphics.toColorInt

class SwipeHelperCallback(
    context: Context,
    private val onSwipeLeft: (Int) -> Unit,
    private val onSwipeRight: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete)!!
    private val editIcon = ContextCompat.getDrawable(context, R.drawable.ic_edit)!!
    private val background = ColorDrawable()

    override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

    override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> onSwipeLeft(vh.adapterPosition)
            ItemTouchHelper.RIGHT -> onSwipeRight(vh.adapterPosition)
        }
    }

    override fun onChildDraw(
        c: Canvas, rv: RecyclerView, vh: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        val itemView = vh.itemView
        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

        when {
            dX > 0 -> {
                background.color = "#388E3C".toColorInt()
                background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                background.draw(c)

                val iconLeft = itemView.left + iconMargin
                val iconTop = itemView.top + iconMargin
                val iconRight = iconLeft + editIcon.intrinsicWidth
                val iconBottom = iconTop + editIcon.intrinsicHeight

                editIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                editIcon.draw(c)
            }

            dX < 0 -> {
                background.color = "#D32F2F".toColorInt()
                background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background.draw(c)

                val iconRight = itemView.right - iconMargin
                val iconTop = itemView.top + iconMargin
                val iconLeft = iconRight - deleteIcon.intrinsicWidth
                val iconBottom = iconTop + deleteIcon.intrinsicHeight

                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)
            }
        }

        super.onChildDraw(c, rv, vh, dX, dY, actionState, isCurrentlyActive)
    }
}
