package com.wazing.wanandroid.ui.system.system

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.api.ResultCallback
import com.wazing.wanandroid.ui.main.MainActivity
import com.wazing.wanandroid.ui.system.activity.SystemActivity
import com.wazing.wanandroid.util.navigateUpTo
import kotlinx.android.synthetic.main.include_refresh_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SystemFragment : Fragment(R.layout.include_refresh_list) {

    private val viewModel by viewModel<SystemViewModel>()
    private val adapter by lazy { SystemAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.treeList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultCallback.Success -> {
                    adapter.setData(it.data)
                }
            }
        })
        swipe_refresh_layout.isEnabled = false
        recycler_view.adapter = adapter.apply {
            setOnItemClickListener { _, item, position ->
                requireActivity().navigateUpTo(SystemActivity::class.java) {
                    putExtra("system", item)
                    putExtra("position", position)
                }
            }
        }
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