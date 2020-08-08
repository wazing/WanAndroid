package com.wazing.wanandroid.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wazing.wanandroid.R
import com.wazing.common.base.BaseFragment
import com.wazing.wanandroid.shared.adapter.ViewPager2Adapter
import com.wazing.wanandroid.ui.home.HomeFragment
import com.wazing.wanandroid.ui.mine.MineFragment
import com.wazing.wanandroid.ui.project.ProjectFragment
import com.wazing.wanandroid.ui.system.TabFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragments: List<Fragment> by lazy {
        listOf(HomeFragment(), TabFragment(), ProjectFragment(), MineFragment())
    }
    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    private var bottomNavigationViewAnimator: ViewPropertyAnimator? = null
    private var currentBottomNavigationState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.apply {
            isUserInputEnabled = false
            offscreenPageLimit = fragments.size
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            adapter = ViewPager2Adapter(this@MainActivity, fragments)
        }
        bottom_navigation_view.setupWithViewPager(view_pager)
    }

    private fun BottomNavigationView.setupWithViewPager(viewPager: ViewPager2) {
        this.setOnNavigationItemSelectedListener {
            val itemPosition = when (it.itemId) {
                R.id.navigation_home -> 0
                R.id.navigation_system -> 1
                R.id.navigation_project -> 2
                R.id.navigation_mine -> 3
                else -> return@setOnNavigationItemSelectedListener false
            }
            viewPager.setCurrentItem(itemPosition, false)
            true
        }
        this.setOnNavigationItemReselectedListener {
            val fragment = fragments[viewPager.currentItem]
            if (fragment is BaseFragment) {
                fragment.scrollToTop()
            }
        }
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                this@setupWithViewPager.menu.getItem(position).isChecked = true
            }
        }
        viewPager.registerOnPageChangeCallback(onPageChangeCallback!!)
    }

    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavigationState == show) {
            return
        }
        if (bottomNavigationViewAnimator != null) {
            bottomNavigationViewAnimator?.cancel()
            bottom_navigation_view.clearAnimation()
        }
        currentBottomNavigationState = show
        val targetY = if (show) 0F else bottom_navigation_view.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimator = bottom_navigation_view.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimator = null
                }
            })
    }

    override fun onDestroy() {
        bottom_navigation_view.clearAnimation()
        bottomNavigationViewAnimator?.let {
            it.cancel()
            null
        }
        onPageChangeCallback?.let {
            view_pager.unregisterOnPageChangeCallback(it)
        }
        super.onDestroy()
    }

}