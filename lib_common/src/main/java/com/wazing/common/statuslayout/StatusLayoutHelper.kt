package com.wazing.common.statuslayout

import android.view.View
import android.view.ViewGroup

class StatusLayoutHelper(private var contentLayout: View) {

    private val parentLayout: ViewGroup = if (contentLayout.parent != null) {
        contentLayout.parent as ViewGroup
    } else {
        contentLayout.rootView.findViewById(android.R.id.content)
    }
    private var currentLayout: View
    private var params: ViewGroup.LayoutParams = contentLayout.layoutParams
    private var viewIndex: Int? = null

    init {
        val count = parentLayout.childCount

        for (index in 0 until count) {
            if (contentLayout == parentLayout.getChildAt(index)) {
                this.viewIndex = index
                break
            }
        }
        this.currentLayout = this.contentLayout
    }

    fun showStatusLayout(view: View?): Boolean {
        if (null == view) {
            return false
        }
        println("currentLayout ---> $currentLayout")
        if (currentLayout != view) {
            currentLayout = view
            println("view.parent ---> ${view.parent}")
            (view.parent as? ViewGroup)?.run {
                println("remove")
                this.removeView(view)
            }
            println("viewIndex ---> $viewIndex")
            viewIndex?.let {
                parentLayout.removeViewAt(it)
                parentLayout.addView(view, it, params)
            }
            return true
        }
        return false
    }

    fun setContentLayout(): Boolean {
        println("contentLayout ---> $contentLayout")
        return showStatusLayout(contentLayout)
    }

}