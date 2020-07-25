package com.wazing.wanandroid.shared.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter : FragmentPagerAdapter {

    private val fragments: List<Fragment>
    private val titles: List<String>

    constructor(
        fragment: Fragment, fragments: List<Fragment>, titles: List<String>
    ) : super(fragment.childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fragments = fragments
        this.titles = titles
    }

    constructor(
        activity: AppCompatActivity, fragments: List<Fragment>, titles: List<String>
    ) : super(activity.supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        this.fragments = fragments
        this.titles = titles
    }

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

}