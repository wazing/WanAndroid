package com.wazing.wanandroid.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>(
    private val list: ArrayList<T> = arrayListOf<T>(),
    @LayoutRes private val layoutId: Int? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var itemClickListener: ((v: View, item: T, position: Int) -> Unit)? = null

    open fun setOnItemClickListener(listener: ((v: View, item: T, position: Int) -> Unit)) {
        this.itemClickListener = listener
    }

    fun setData(list: List<T>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (layoutId == null) {
            onCreateBaseViewHolder(parent, viewType)
        } else {
            BaseViewHolder.create(parent, layoutId)
        }.apply {
            itemView.setOnClickListener {
                val position = this.absoluteAdapterPosition
                itemClickListener?.invoke(it, list[position], position)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindBaseViewHolder(holder, list[position], position)
    }

    abstract fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    abstract fun onBindBaseViewHolder(holder: BaseViewHolder, item: T, position: Int)
}