package com.wazing.wanandroid.ui.system.system

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.donkingliang.labels.LabelsView
import com.wazing.wanandroid.R
import com.wazing.wanandroid.base.BaseViewHolder
import com.wazing.wanandroid.model.entity.SystemParent

class SystemAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val list = arrayListOf<SystemParent>()

    private var itemClickListener: ((v: View, item: SystemParent, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: ((v: View, item: SystemParent, position: Int) -> Unit)) {
        this.itemClickListener = listener
    }

    fun setData(list: List<SystemParent>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.create(parent, R.layout.item_system_view).apply {
            this.getView<LabelsView>(R.id.item_labels).setOnLabelClickListener { v, _, p ->
                val position = this.absoluteAdapterPosition
                itemClickListener?.invoke(v, list[position], p)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            setText(R.id.item_title, item.name)
            getView<LabelsView>(R.id.item_labels) {
                setLabels(item.children) { _, _, data -> data.name }
            }
        }
    }


}