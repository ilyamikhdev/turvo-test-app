package com.itechart.turvo.entity

import android.os.Parcelable
import com.github.mikephil.charting.data.LineData
import com.itechart.turvo.ui.list.ListItemTicker
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticker(
    val id: Int,
    val name: String,
    val prices: List<Double>
) : Parcelable

fun Ticker.convertToListItem(dataLine: LineData?) = ListItemTicker(
    item = this,
    id = id,
    title = name,
    price = prices.lastOrNull()?.toString() ?: "",
    dataLines = dataLine
)
