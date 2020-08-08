package com.wazing.wanandroid.ui.mine.coin

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.wazing.common.adapter.BaseViewHolder
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.entity.CoinInfo

class CoinAdapter : PagingDataAdapter<CoinInfo, BaseViewHolder>(COIN_COMPARATOR) {

    companion object {
        private val COIN_COMPARATOR = object : DiffUtil.ItemCallback<CoinInfo>() {
            override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.apply {
            setText(R.id.item_reason, item.reason)
            setText(R.id.item_desc, item.desc)
            setText(R.id.item_coin_count, "+${item.coinCount}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.create(parent, R.layout.item_coin_view)
    }

}