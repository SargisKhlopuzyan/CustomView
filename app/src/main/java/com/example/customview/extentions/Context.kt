package com.example.customview.extentions

import android.content.Context
import android.util.Log


fun Context.dpToOx(dp: Int): Float {
    Log.e("LOG_TAG", "density: ${this.resources.displayMetrics.density}")
    return dp.toFloat() * this.resources.displayMetrics.density
}