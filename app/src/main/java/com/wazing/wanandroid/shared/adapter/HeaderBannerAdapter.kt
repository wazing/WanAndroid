package com.wazing.wanandroid.shared.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.wazing.wanandroid.R
import com.wazing.wanandroid.base.BaseViewHolder
import com.wazing.wanandroid.model.BannerEntity
import com.wazing.wanandroid.util.BANNER_TIME_DEFAULT
import com.wazing.wanandroid.util.BannerImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

class HeaderBannerAdapter(
    private val list: MutableList<BannerEntity> = mutableListOf(),
    private val retryCallback: () -> Unit
) : LoadStateAdapter<BaseViewHolder>() {

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        println("header -> loadState = $loadState")
        return true
    }

    fun setData(data: List<BannerEntity>) {
        list.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BaseViewHolder {
        println("BannerHeaderAdapter -> onCreateViewHolder")
        return BaseViewHolder.create(parent, R.layout.item_header_banner)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, loadState: LoadState) {
        println("BannerHeaderAdapter -> onBindViewHolder")
        val images = list.map { banner -> banner.imagePath }
        val titles = list.map { banner -> banner.title }
        holder.getView<Banner>(R.id.header_banner) {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setImageLoader(BannerImageLoader())
            setBannerAnimation(Transformer.DepthPage)
            setImages(images)
            setBannerTitles(titles)
            setIndicatorGravity(BannerConfig.RIGHT)
            isAutoPlay(true)
            setDelayTime(BANNER_TIME_DEFAULT)
            start()
        }
    }

}