package com.itechart.turvo.helper

import java.math.RoundingMode

fun parseTickers(tickers: String?): List<String> =
    tickers
        .orEmpty()
        .split(",")
        .map { it.trim() }
        .filterNot { it.isEmpty() }

fun Double?.round(): Double =
    this
        ?.toBigDecimal()
        ?.setScale(2, RoundingMode.UP)
        ?.toDouble() ?: 0.0