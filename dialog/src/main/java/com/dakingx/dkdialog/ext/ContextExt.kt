package com.dakingx.dkdialog.ext

import android.content.Context

fun Context.getScreenWidth(): Int = this.resources.displayMetrics.widthPixels

fun Context.getScreenHeight(): Int = this.resources.displayMetrics.heightPixels

fun Context.dp2Px(dpValue: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dpValue * scale + 0.5F).toInt()
}
