package com.didahdx.weatherforecast.presentation.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * @author Daniel Didah on 1/23/22.
 */
fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}