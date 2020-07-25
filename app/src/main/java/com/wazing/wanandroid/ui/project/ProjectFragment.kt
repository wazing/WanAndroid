package com.wazing.wanandroid.ui.project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.wazing.wanandroid.R

class ProjectFragment : Fragment(R.layout.fragment_project) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            println("aaaaaa")
        }
    }

}