package com.wazing.wanandroid.util

import android.app.Activity
import android.content.Intent

fun Activity.navigateUpTo(clz: Class<*>) {
    this.startActivity(Intent(this, clz))
}

fun Activity.navigateUpTo(clz: Class<*>, block: Intent.() -> Unit) {
    val intent = Intent(this, clz)
    block.invoke(intent)
    this.startActivity(intent)
}