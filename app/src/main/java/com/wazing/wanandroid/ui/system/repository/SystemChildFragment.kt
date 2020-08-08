package com.wazing.wanandroid.ui.system.repository

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.wazing.wanandroid.R
import com.wazing.common.data.ApiException
import com.wazing.wanandroid.model.entity.SystemChildren
import com.wazing.wanandroid.shared.adapter.ArticleAdapter
import com.wazing.wanandroid.shared.adapter.LoadMoreAdapter
import com.wazing.wanandroid.ui.system.system.SystemViewModel
import com.wazing.wanandroid.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.include_refresh_list.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class SystemChildFragment : Fragment(R.layout.include_refresh_list) {

    private val viewModel by viewModel<SystemViewModel>()
    private val adapter by lazy { ArticleAdapter(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<SystemChildren>(KEY_ITEM_CHILD) ?: return
        viewModel.setCid(item.id)

        adapter.addLoadStateListener { loadState ->
            // println("loadState -> $loadState")
            swipe_refresh_layout.isRefreshing = loadState.refresh is LoadState.Loading
            val refreshErrorState = loadState.refresh is LoadState.Error
                    && loadState.append is LoadState.NotLoading
            val errorMsg = if (refreshErrorState) {
                val errorState = (loadState.refresh as? LoadState.Error)
                    ?.error as Exception
                ApiException.checkException(errorState)
            } else null
            load_status_text.apply {
                isVisible = refreshErrorState
                text = getString(R.string.load_state_error, errorMsg)
            }

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(), "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_SHORT
                ).show()
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.articles.collect(adapter::submitData)
        }
        swipe_refresh_layout.setOnRefreshListener(adapter::refresh)
        recycler_view.adapter = adapter.withLoadStateFooter(LoadMoreAdapter(adapter::retry))
        recycler_view.addItemDecoration(SpacesItemDecoration(requireContext()))
    }

    companion object {
        private const val KEY_ITEM_CHILD = "item_child"
        fun newInstance(item: SystemChildren): SystemChildFragment {
            return SystemChildFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(KEY_ITEM_CHILD, item)
                    }
                }
        }
    }
}