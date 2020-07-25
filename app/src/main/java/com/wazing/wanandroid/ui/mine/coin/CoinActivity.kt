package com.wazing.wanandroid.ui.mine.coin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.wazing.wanandroid.R
import com.wazing.wanandroid.shared.adapter.LoadMoreAdapter
import com.wazing.wanandroid.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.fragment_home.recycler_view
import kotlinx.android.synthetic.main.fragment_home.swipe_refresh_layout
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject

class CoinActivity : AppCompatActivity() {

    private val viewModel by inject<CoinViewModel>()
    private val coinAdapter by lazy { CoinAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)

        toolbar.apply {
            setSupportActionBar(this)
            setTitle(R.string.title_coin_details)
            setNavigationIcon(R.drawable.ic_arrow_back_24)
            setNavigationOnClickListener { finish() }
        }

        coinAdapter.addLoadStateListener { loadState ->
            // println("=== $loadState")
            swipe_refresh_layout.isRefreshing = loadState.refresh == LoadState.Loading
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
            }
        }
        swipe_refresh_layout.setOnRefreshListener(coinAdapter::refresh)
        recycler_view.apply {
            adapter = coinAdapter.withLoadStateFooter(LoadMoreAdapter(coinAdapter::retry))
            addItemDecoration(SpacesItemDecoration(context))
        }
        lifecycleScope.launchWhenCreated {
            viewModel.coinList.collect(coinAdapter::submitData)
        }
    }

}