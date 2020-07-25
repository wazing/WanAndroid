package com.wazing.wanandroid.ui.system.navigate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.donkingliang.labels.LabelsView
import com.wazing.wanandroid.R
import com.wazing.wanandroid.base.BaseViewHolder
import com.wazing.wanandroid.model.entity.NavigateParent

class NavigateAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val list = arrayListOf<NavigateParent>()

    fun setData(list: List<NavigateParent>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder.create(parent, R.layout.item_system_view).apply {
            this.getView<LabelsView>(R.id.item_labels)
                .setOnLabelClickListener { _, data, position ->
                    println("data -> $data")
                }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = list[position]
        holder.apply {
            setText(R.id.item_title, item.name)
            getView<LabelsView>(R.id.item_labels) {
                setLabels(item.articles) { _, _, data -> data.title }
            }
        }
    }


}