package com.wazing.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

abstract class BaseRecyclerViewAdapter<T> @JvmOverloads constructor(
    private val itemLayoutRes: Int, list: ArrayList<T> = ArrayList(),
    diffCallback: DiffUtil.ItemCallback<T>
) : BaseMultiRecyclerViewAdapter<T>(list, diffCallback) {

    override fun addItemViewType(position: Int): Int = TYPE_ITEM

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.create(parent, itemLayoutRes)
    }
}