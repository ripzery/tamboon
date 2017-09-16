package com.ripzery.tamboon.extensions

import android.content.Context
import android.widget.Toast

/**
 * Created by ripzery on 9/16/17.
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toaster.toast?.cancel()
    Toaster.toast = Toast.makeText(this, msg, duration)
    Toaster.toast?.show()
}

object Toaster {
    var toast: Toast? = null
}