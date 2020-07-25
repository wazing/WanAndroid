package com.wazing.wanandroid.ui.system.navigate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.api.ResultCallback
import com.wazing.wanandroid.ui.main.MainActivity
import kotlinx.android.synthetic.main.include_refresh_list.*
import org.koin.android.ext.android.inject

class NavigateFragment : Fragment(R.layout.include_refresh_list) {

    private val viewModel by inject<NavigateViewModel>()
    private val adapter by lazy { NavigateAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navTree.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultCallback.Success -> {
                    adapter.setData(it.data)
                }
            }
        })
        swipe_refresh_layout.isEnabled = false
        recycler_view.adapter = adapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (activity is MainActivity && dy != 0) {
                    (activity as? MainActivity)?.animateBottomNavigationView(dy < 0)
                }
            }
        })
    }
}