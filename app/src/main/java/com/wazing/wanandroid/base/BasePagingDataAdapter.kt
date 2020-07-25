package com.wazing.wanandroid.base

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagingDataAdapter<T : Any>(
    comparator: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BaseViewHolder>(comparator) {

    private var onClickListener: ((view: View, item: T, position: Int) -> Unit)? = null

    open fun setOnClickListener(listener: (view: View, item: T, position: Int) -> Unit) {
        this.onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return onCreateBaseViewHolder(parent, viewType).apply {
            itemView.setOnClickListener {
                val position = this.bindingAdapterPosition
                val item = getItem(position) ?: return@setOnClickListener
                onClickListener?.invoke(it, item, position)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position) ?: return
        onBindBaseViewHolder(holder, item, position)
    }

    abstract fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    abstract fun onBindBaseViewHolder(holder: BaseViewHolder, item: T, position: Int)
}