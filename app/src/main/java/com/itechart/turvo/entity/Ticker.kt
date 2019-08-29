package com.itechart.turvo.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticker(
    val id: Int,
    val name: String,
    val prices: List<Double>
) : Parcelable