package com.wazing.wanandroid.util

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideExt : AppGlideModule() {

}

fun ImageView.byGlide(url: String) {
    GlideApp.with(this).load(url).into(this)
}
