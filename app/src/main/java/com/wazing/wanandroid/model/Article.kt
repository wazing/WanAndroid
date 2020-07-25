package com.wazing.wanandroid.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val primaryKeyId: Int = 0,
    val apkLink: String? = "",
    val audit: Int = 0,
    val author: String,
    val chapterId: Int = 0,
    val chapterName: String? = "",
    var collect: Boolean = false,
    val courseId: Int = 0,
    val desc: String? = "",
    val envelopePic: String? = "",
    val fresh: Boolean = false,
    val id: Int = 0,
    val link: String? = "",
    val niceDate: String? = "",
    val niceShareDate: String? = "",
    val origin: String? = "",
    val originId: Int = 0,
    val prefix: String? = "",
    val projectLink: String? = "",
    val publishTime: Long = 0,
    val selfVisible: Int = 0,
    val shareDate: Long = 0,
    val shareUser: String?,
    val superChapterId: Int = 0,
    val superChapterName: String? = "",
    val tags: List<Tag>? = emptyList(),
    val title: String,
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    var top: Boolean = false
) : Parcelable