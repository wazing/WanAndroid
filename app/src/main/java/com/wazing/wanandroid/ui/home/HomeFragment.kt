package com.wazing.wanandroid.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.google.android.material.appbar.AppBarLayout
import com.wazing.wanandroid.R
import com.wazing.wanandroid.base.BaseFragment
import com.wazing.wanandroid.model.BannerEntity
import com.wazing.wanandroid.model.api.ResultCallback
import com.wazing.wanandroid.shared.adapter.ArticleAdapter
import com.wazing.wanandroid.shared.adapter.LoadMoreAdapter
import com.wazing.wanandroid.ui.main.MainActivity
import com.wazing.wanandroid.ui.mine.collect.CollectViewModel
import com.wazing.wanandroid.util.BANNER_TIME_DEFAULT
import com.wazing.wanandroid.util.BannerImageLoader
import com.wazing.wanandroid.util.SpacesItemDecoration
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel by viewModel<HomeViewModel>()
    private val collectViewModel by viewModel<CollectViewModel>()
    private val articleAdapter by lazy { ArticleAdapter(requireContext()) }

    @SuppressLint("ResourceType")
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleAdapter.apply {
//            addDataRefreshListener {
//                swipe_refresh_layout.isRefreshing = it
//            }
            addLoadStateListener { loadState ->
//                println("=== $loadState")
                swipe_refresh_layout.isRefreshing = loadState.refresh == LoadState.Loading
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    longToast("\uD83D\uDE28 Wooops ${it.error}")
                }
            }
            setOnClickListener { view, item, position ->
                println("-> $item")
            }
            setOnCollectClickListener { view, item, position, isCollect ->
                collectViewModel.collectArticleById(item.id, isCollect)
            }
        }

        lifecycleScope.launch {
            viewModel.articles.collect(articleAdapter::submitData)
        }

        viewModel.banners.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultCallback.Success -> {
                    initBanner(it.data)
                }
                is ResultCallback.Error -> {
                    println("home banner error ${it.message}")
                }
            }
        })

        swipe_refresh_layout.setOnRefreshListener {
            viewModel.getBannerList()
            articleAdapter.refresh()
        }
        recycler_view.apply {
            adapter = articleAdapter.withLoadStateFooter(LoadMoreAdapter { articleAdapter.retry() })
            addItemDecoration(SpacesItemDecoration(context))
        }
        var currentOffset = 0
        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (activity is MainActivity && currentOffset != offset) {
                (activity as MainActivity).animateBottomNavigationView(offset > currentOffset)
                currentOffset = offset
            }
        })
    }

    override fun onResume() {
        super.onResume()
        banner.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        banner.stopAutoPlay()
    }

    override fun scrollToTop() {
        app_bar_layout?.setExpanded(true)
        recycler_view?.smoothScrollToPosition(0)
    }

    private fun initBanner(result: List<BannerEntity>) {
        val images = result.map { banner -> banner.imagePath }
        val titles = result.map { banner -> banner.title }
        banner.apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setImageLoader(BannerImageLoader())
            setBannerAnimation(Transformer.DepthPage)
            setImages(images)
            setBannerTitles(titles)
            setIndicatorGravity(BannerConfig.RIGHT)
            isAutoPlay(true)
            setDelayTime(BANNER_TIME_DEFAULT)
        }.start()
    }

}