package com.wazing.wanandroid.util

import android.content.Context
import android.widget.ImageView
import com.youth.banner.loader.ImageLoader

class BannerImageLoader : ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideApp.with(context).load(path).into(imageView);
    }
}