package com.itechart.turvo.helper

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toast(text: String, long: Boolean = false) {
    Toast.makeText(this, text, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

/**
 * Set visible state of View between VISIBLE and GONE
 *
 * @property show true for VISIBLE, false for GONE
 */
fun View.show(show: Boolean = true) {
    visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Set visible state of View between GONE and VISIBLE
 *
 * @property hide true for GONE, false for VISIBLE
 */
fun View.hide(hide: Boolean = true) {
    show(!hide)
}

