package com.dakingx.dkdialog.ext

import android.content.Context

fun Context.dp2Px(dpValue: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dpValue * scale + 0.5F).toInt()
}

fun Context.getScreenWidth() = this.resources.displayMetrics.widthPixels

fun Context.getScreenHeight() = this.resources.displayMetrics.heightPixels
