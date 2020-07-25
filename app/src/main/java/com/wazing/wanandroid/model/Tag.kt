package com.wazing.wanandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    var id: Long,
    var articleId: Long,
    var name: String?,
    var url: String?
) : Parcelable