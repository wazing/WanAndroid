package com.wazing.wanandroid.ui.system.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.entity.SystemParent
import com.wazing.wanandroid.shared.adapter.ViewPagerAdapter
import com.wazing.wanandroid.ui.system.repository.SystemChildFragment
import kotlinx.android.synthetic.main.activity_system.*

class SystemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system)

        val item = intent.getParcelableExtra<SystemParent>("system") ?: return
        val position = intent.getIntExtra("position", 0)

        toolbar.apply {
            setSupportActionBar(this)
            title = item.name
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { this@SystemActivity.finish() }
        }

        val fragments = arrayListOf<Fragment>()
        val titles = arrayListOf<String>()
        for (child in item.children) {
            fragments.add(SystemChildFragment.newInstance(child))
            titles.add(child.name)
        }
        view_pager.apply {
            offscreenPageLimit = fragments.size
            adapter = ViewPagerAdapter(this@SystemActivity, fragments, titles)
            currentItem = position
            tab_layout.setupWithViewPager(this)
        }
    }

}