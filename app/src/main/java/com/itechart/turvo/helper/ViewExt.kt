package com.itechart.turvo.helper

import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart

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

fun LineChart.initUI() {
    axisLeft.isEnabled = false
    axisLeft.spaceTop = 40f
    axisLeft.spaceBottom = 40f
    axisRight.isEnabled = false
    xAxis.isEnabled = false
    description.isEnabled = false
    legend.isEnabled = false
    setTouchEnabled(false)
    setNoDataText("No data")
    setNoDataTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
}
