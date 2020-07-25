package com.wazing.wanandroid.ui.system

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wazing.wanandroid.R
import com.wazing.wanandroid.shared.adapter.ViewPagerAdapter
import com.wazing.wanandroid.ui.system.navigate.NavigateFragment
import com.wazing.wanandroid.ui.system.system.SystemFragment
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment(R.layout.fragment_tab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(
            SystemFragment(),
            NavigateFragment()
        )
        val titles = listOf("体系", "导航")
        view_pager.adapter = ViewPagerAdapter(this, fragments, titles)
        tab_layout.setupWithViewPager(view_pager)
    }

}