package com.wazing.wanandroid.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration constructor(
    private val context: Context
) : RecyclerView.ItemDecoration() {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
        private const val DIVIDER_HEIGHT = 0.5f
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
    }

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.LTGRAY
    }
    private var dividerHeight: Float
    private var margin = 0f

    init {
        dividerHeight = dip2px(DIVIDER_HEIGHT).toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = dividerHeight.toInt()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount - 1) {
            val view = parent.getChildAt(i)
            val dividerLeft = parent.paddingLeft + margin
            val dividerTop = view.bottom + dividerHeight
            val dividerRight = parent.width - parent.paddingRight - margin
            val dividerBottom = view.bottom.toFloat()
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, paint)
        }
    }

    fun setMargin(margin: Float): SpacesItemDecoration {
        this.margin = margin
        return this
    }

    fun setColor(color: Int): SpacesItemDecoration {
        paint.color = color
        return this
    }

    fun setDividerHeight(height: Float): SpacesItemDecoration? {
        dividerHeight = height
        return this
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private fun dip2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}