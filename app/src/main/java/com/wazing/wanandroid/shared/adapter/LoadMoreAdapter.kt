package com.wazing.wanandroid.shared.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.wazing.wanandroid.R
import com.wazing.wanandroid.base.BaseViewHolder

class LoadMoreAdapter(private val retryCallback: () -> Unit) : LoadStateAdapter<BaseViewHolder>() {

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        // return super.displayLoadStateAsItem(loadState)
        // println("loadState -> $loadState")
        return if (loadState is LoadState.NotLoading && loadState.endOfPaginationReached) true
        else super.displayLoadStateAsItem(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseViewHolder {
        return BaseViewHolder.create(parent, R.layout.item_load_more_view).apply {
            itemView.setOnClickListener {
                retryCallback.takeIf { loadState is LoadState.Error }?.invoke()
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, loadState: LoadState) {
        holder.apply {
            setVisible(R.id.item_progress_bar, loadState is LoadState.Loading)
            setVisible(R.id.item_loading, loadState is LoadState.Loading)
            setVisible(R.id.item_error, loadState is LoadState.Error)
            val endVisible = loadState is LoadState.NotLoading && loadState.endOfPaginationReached
            setVisible(R.id.item_end, endVisible)
        }
    }

}