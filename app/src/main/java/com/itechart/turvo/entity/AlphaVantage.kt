package com.itechart.turvo.entity

import com.google.gson.annotations.SerializedName

data class AlphaVantage(
    @SerializedName("Time Series (Daily)")
    val timeSeries: Map<String, DayData>
)

data class DayData(
    @SerializedName("4. close")
    val price: Double
)