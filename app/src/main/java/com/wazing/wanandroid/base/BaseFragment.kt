package com.wazing.wanandroid.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    open fun scrollToTop() {}

    fun shortToast(any: Any?) {
        any?.let {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun longToast(any: Any?) {
        any?.let {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}