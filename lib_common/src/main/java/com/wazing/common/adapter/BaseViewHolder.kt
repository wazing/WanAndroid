package com.wazing.common.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder private constructor(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val mViews: SparseArray<View> = SparseArray()

    fun <T : View> getView(viewId: Int): T {
        var view = mViews.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        @Suppress("UNCHECKED_CAST")
        return view as T
    }

    inline fun <reified T : View> getView(@IdRes viewId: Int, block: T.() -> Unit): BaseViewHolder {
        val view = getView<T>(viewId)
        block.invoke(view)
        return this
    }

    fun setText(@IdRes viewId: Int, text: CharSequence?) {
        getView<TextView>(viewId).text = text
    }

    fun setImage(@IdRes viewId: Int, img: (imageView: ImageView) -> Unit) = img(getView(viewId))

    fun setVisible(@IdRes viewId: Int, block: () -> Boolean) {
        getView<View>(viewId).isVisibled(block.invoke())
    }

    fun setVisible(@IdRes viewId: Int, isVisible: Boolean) {
        getView<View>(viewId).isVisibled(isVisible)
    }

    fun isSelected(@IdRes viewId: Int, isSelected: Boolean) {
        getView<View>(viewId).isSelected = isSelected
    }

    fun View.isVisibled(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    companion object {

        fun create(itemView: View): BaseViewHolder {
            return BaseViewHolder(itemView)
        }

        fun create(parent: ViewGroup, @LayoutRes layoutId: Int): BaseViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return create(itemView)
        }
    }
}