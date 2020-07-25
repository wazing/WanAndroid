package com.wazing.wanandroid.ui.mine.collect

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.wazing.wanandroid.R
import com.wazing.wanandroid.model.api.ApiException
import com.wazing.wanandroid.shared.adapter.LoadMoreAdapter
import com.wazing.wanandroid.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class CollectArticleActivity : AppCompatActivity() {

    private val viewModel by viewModel<CollectViewModel>()
    private val adapter by lazy { CollectAdapter(this) }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)

        toolbar.apply {
            setSupportActionBar(this)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { finish() }
        }

        swipe_refresh_layout.setOnRefreshListener(adapter::refresh)
        recycler_view.adapter = adapter.withLoadStateFooter(LoadMoreAdapter(adapter::retry))
        recycler_view.addItemDecoration(SpacesItemDecoration(this))

        adapter.setOnClickListener { view, item, position ->
            println("removed -> position = $position ${item.title}")
//            adapter.notifyItemRemoved(position)
        }
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
                    this@CollectArticleActivity,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.collects.collect(adapter::submitData)
        }
    }

}