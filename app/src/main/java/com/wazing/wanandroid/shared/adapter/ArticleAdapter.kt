package com.wazing.wanandroid.shared.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.wazing.common.adapter.BaseViewHolder
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.Article

class ArticleAdapter(
    private val context: Context
) : PagingDataAdapter<Article, BaseViewHolder>(ARTICLE_COMPARATOR) {

    private var onClickListener: ((view: View, item: Article, position: Int) -> Unit)? = null
    private var onCollectClickListener: ((
        view: View, item: Article, position: Int,
        isCollect: Boolean
    ) -> Unit)? = null

    fun setOnClickListener(listener: (view: View, item: Article, position: Int) -> Unit) {
        this.onClickListener = listener
    }

    fun setOnCollectClickListener(listener: (view: View, item: Article, position: Int, isCollect: Boolean) -> Unit) {
        this.onCollectClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.create(parent, R.layout.item_article_view).apply {
            itemView.setOnClickListener { v ->
                onClickListener?.let {
                    val position = this.adapterPosition
                    val item = getItem(position) ?: return@setOnClickListener
                    it.invoke(v, item, position)
                }
            }
            getView<ImageView>(R.id.item_collect).setOnClickListener {
                val position = this.adapterPosition
                val item = getItem(position) ?: return@setOnClickListener
                val isCollect = it.isSelected
                item.collect = !isCollect
                it.isSelected = !isCollect
                onCollectClickListener?.invoke(it, item, position, isCollect)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val author = item.author.takeUnless { it.isBlank() }
            ?: item.shareUser.takeUnless { it.isNullOrBlank() }
            ?: context.getString(R.string.article_item_no_author)
        holder.apply {
            setText(R.id.item_title, item.title.htmlToSpanned().toString())
            setText(R.id.item_author, author)
            setText(R.id.item_nice_date, item.niceDate)
            setText(R.id.item_chart_name, "${item.chapterName}/${item.superChapterName}")
            setVisible(R.id.item_top, item.top)
            setVisible(R.id.item_fresh, item.fresh)
            isSelected(R.id.item_collect, item.collect)
            getView<TextView>(R.id.item_tag) {
                isVisible = item.tags?.firstOrNull()?.let { tag ->
                    text = tag.name
                    true
                } ?: false
            }
            getView<TextView>(R.id.item_desc) {
                isVisible = !TextUtils.isEmpty(item.desc)
                text = item.desc.htmlToSpanned()?.toString()
            }
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
        }
    }

    private fun String?.htmlToSpanned() = if (this.isNullOrBlank()) null
    else HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT)

}