package com.wazing.wanandroid.shared.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter : FragmentStateAdapter {

    private val fragments: List<Fragment>

    constructor(activity: FragmentActivity, fragments: List<Fragment>) : super(activity) {
        this.fragments = fragments
    }

    constructor(fragment: Fragment, fragments: List<Fragment>) : super(fragment) {
        this.fragments = fragments
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}