package com.wazing.common.statuslayout

import android.view.View

interface StatusClickListener {

    fun onEmptyClick(view: View)

    fun onErrorClick(view: View)
}