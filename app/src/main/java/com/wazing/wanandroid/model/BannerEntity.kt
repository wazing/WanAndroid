package com.wazing.wanandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerEntity(
    val id: String,
    val title: String,
    val desc: String,
    val imagePath: String,
    val url: String,
    val isVisible: Int,
    val type: Int,
    val order: Int
) : Parcelable