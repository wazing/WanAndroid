package com.wazing.wanandroid.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SystemParent(
    val id: Int,
    val courseId: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: List<SystemChildren>
) : Parcelable

@Parcelize
data class SystemChildren(
    val id: Int,
    val courseId: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: List<String>
) : Parcelable