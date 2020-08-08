package com.wazing.common.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.*
import com.wazing.common.R

abstract class BaseMultiRecyclerViewAdapter<T> constructor(
    private val dataSourceList: ArrayList<T> = ArrayList(),
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder>(diffCallback) {

    private var isOpenLoadMore = false
    private var isLoadingMore = false
    private var isLoadMoreEnd = false

    private var headerLayout: LinearLayout? = null

    private var loadMoreViewHolder: BaseViewHolder? = null

    /** item点击事件监听 */
    private var onItemClickListener: ((view: View, item: T, position: Int) -> Unit)? = null
    /** 加载更多事件监听*/
    private var onLoadMoreListener: (() -> Unit)? = null

    fun setOnLoadMoreListener(listener: () -> Unit) {
        this.onLoadMoreListener = listener
        this.isOpenLoadMore = true
    }

    override fun getItemCount(): Int {
        var count = 0
        if (hasHeaderLayout()) count++
        if (isOpenLoadMore && onLoadMoreListener != null) count++
        count += dataSourceList.size
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && hasHeaderLayout()) {
            TYPE_HEADER
        } else if (isOpenLoadMore && onLoadMoreListener != null && itemCount == position + 1) {
            TYPE_LOAD_MORE
        } else {
            var pos: Int = position
            if (hasHeaderLayout()) pos -= 1
            addItemViewType(pos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            TYPE_HEADER -> return BaseViewHolder.create(headerLayout!!)
            TYPE_LOAD_MORE -> {
                loadMoreViewHolder = BaseViewHolder.create(parent, R.layout.common_recycler_item_load_more)
                loadMoreViewHolder?.apply {
                    itemView.setOnClickListener {
                        if (isLoadingMore || isLoadMoreEnd) return@setOnClickListener
                        startLoadMore()
                    }
                }
                return loadMoreViewHolder!!
            }
            else -> {
                val holder = onCreateBaseViewHolder(parent, viewType)
                holder.itemView.setOnClickListener {
                    var position = holder.adapterPosition
                    if (hasHeaderLayout()) {
                        position--
                    }
                    onItemClickListener?.invoke(it, dataSourceList[position], position)
                }
                return holder
            }
        }
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_HEADER -> return
            TYPE_LOAD_MORE -> startLoadMore()
            else -> {
                if (hasHeaderLayout()) {
                    convert(viewHolder, dataSourceList[position - 1], position - 1)
                } else {
                    convert(viewHolder, dataSourceList[position], position)
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager != null && layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (isOpenLoadMore && itemCount == position + 1) {
                        layoutManager.spanCount
                    } else 1
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val lp = holder.itemView.layoutParams
        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
            val position = holder.adapterPosition
            if (isOpenLoadMore && itemCount == position + 1) {
                lp.isFullSpan = true
            }
        }
    }

    fun setOnItemClickListener(listener: (view: View, item: T, position: Int) -> Unit) {
        this.onItemClickListener = listener
    }

    fun getItemSize() = dataSourceList.size

    override fun getItem(position: Int): T = dataSourceList[position]

    fun getItems() = dataSourceList

    fun cleanItem() {
        dataSourceList.clear()
        notifyDataSetChanged()
    }

    fun setNewItem(list: Collection<T>?) {
        dataSourceList.clear()
        if (!list.isNullOrEmpty()) {
            dataSourceList.addAll(list)
        }
        notifyDataSetChanged()
        // 所有数据加载完成 = false
        isLoadMoreEnd = false
        isLoadingMore = false
    }

    fun setItem(data: T?) {
        if (data == null) {
            setItem(list = null)
        } else {
            setItem(list = arrayListOf(data))
        }
    }

    fun setItem(list: Collection<T>?) {
        if (list.isNullOrEmpty()) {
            loadMoreEnd()
        } else {
            dataSourceList.addAll(list)
            notifyItemRangeInserted(itemCount, list.size)
        }
        isLoadingMore = false
    }

    private fun startLoadMore() {
        if (!isOpenLoadMore || onLoadMoreListener == null || dataSourceList.isEmpty()
            || isLoadingMore || isLoadMoreEnd
        ) return
        loadMoreLoading()
        onLoadMoreListener?.invoke()
    }

    private fun hasHeaderLayout(): Boolean {
        return (headerLayout != null && headerLayout!!.childCount > 0)
    }

    fun getHeaderCount(): Int {
        return if (headerLayout == null) 0 else headerLayout!!.childCount
    }

    @Nullable
    fun getHeaderView(index: Int): View? {
        return headerLayout?.getChildAt(index)
    }

    @JvmOverloads
    fun addHeaderView(
        header: View, index: Int = getHeaderCount(), orientation: Int = LinearLayout.VERTICAL
    ): Int {
        if (headerLayout == null) {
            headerLayout = LinearLayout(header.context)
            headerLayout?.let {
                it.orientation = orientation
                if (orientation == LinearLayout.VERTICAL) {
                    it.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                } else {
                    it.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                }
            }
        }
        var childCount = headerLayout!!.childCount
        val i = when {
            index < 0 -> 0
            index > childCount -> childCount
            else -> index
        }
        headerLayout!!.addView(header, i)
        childCount = headerLayout!!.childCount
        if (childCount == 1) {
            notifyItemInserted(0)
        }
        return childCount
    }

    private fun loadMoreLoading() {
        isLoadingMore = true
        loadMoreStatus(true, "正在加载...")
    }

    @JvmOverloads
    fun loadMoreFail(message: String = "加载失败，点击重试") {
        isLoadingMore = false
        isLoadMoreEnd = false
        loadMoreStatus(false, message)
    }

    @JvmOverloads
    fun loadMoreEnd(message: String = "没有更多了 (=・ω・=)") {
        isLoadingMore = false
        isLoadMoreEnd = true
        loadMoreStatus(false, message)
    }

    private fun loadMoreStatus(isVisible: Boolean, message: String) {
        loadMoreViewHolder?.let {
            if (it.itemView is ViewGroup) {
                val viewGroup = it.itemView
                for (i in 0 until viewGroup.childCount) {
                    val childView = viewGroup.getChildAt(i)
                    if (childView is ProgressBar) {
                        childView.setVisibility(if (isVisible) View.VISIBLE else View.GONE)
                    } else if (childView is TextView) {
                        childView.text = message
                    }
                }
            }
        }
    }

    companion object {
        internal const val TYPE_ITEM = 1000001
        private const val TYPE_HEADER = 1000002
        private const val TYPE_LOAD_MORE = 1000003
    }

    abstract fun addItemViewType(position: Int): Int

    abstract fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder

    abstract fun convert(holder: BaseViewHolder, item: T, position: Int)

}