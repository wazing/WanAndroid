package com.wazing.wanandroid.ui.mine.collect

import android.content.Context
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.wazing.common.adapter.BaseViewHolder
import com.wazing.wanandroid.R
import com.wazing.wanandroid.base.BasePagingDataAdapter
import com.wazing.wanandroid.model.entity.Collect

class CollectAdapter(private val context: Context) : BasePagingDataAdapter<Collect>(COMPARATOR) {

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.create(parent, R.layout.item_collect_view)
    }

    override fun onBindBaseViewHolder(holder: BaseViewHolder, item: Collect, position: Int) {
        val author = item.author.takeUnless { it.isBlank() }
            ?: context.getString(R.string.article_item_no_author)
        holder.apply {
            setText(R.id.item_title, item.title.htmlToSpanned().toString())
            setText(R.id.item_author, author)
            setText(R.id.item_nice_date, item.niceDate)
            setText(R.id.item_chart_name, item.chapterName)
            getView<TextView>(R.id.item_desc) {
                isVisible = !TextUtils.isEmpty(item.desc)
                text = item.desc.htmlToSpanned()?.toString()
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Collect>() {
            override fun areItemsTheSame(oldItem: Collect, newItem: Collect): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Collect, newItem: Collect): Boolean {
                return oldItem == newItem
            }
        }
    }

    private fun String?.htmlToSpanned() = if (this.isNullOrBlank()) null
    else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)


}