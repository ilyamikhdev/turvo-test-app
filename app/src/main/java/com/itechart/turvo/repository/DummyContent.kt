package com.itechart.turvo.repository

import com.itechart.turvo.entity.Ticker
import java.math.RoundingMode
import kotlin.random.Random

/**
 * Helper class for providing sample name for user interfaces created by
 * Android template wizards.
 */
class DummyContent(tickers: List<String>, private var countDays: Int) {
    val tickersList = mutableListOf<Ticker>()

    init {
        tickers.forEachIndexed { index, element ->
            tickersList.add(createDummyItem(index, element))
        }
    }

    private fun createDummyItem(position: Int, ticker: String) = Ticker(
        id = position,
        name = ticker,
        prices = makePriceList()
    )

    private fun makePriceList(): List<Double> {
        val priceList = mutableListOf<Double>()

        if (countDays == 0) {
            countDays = 1
        }

        repeat((1..countDays).count()) {
            priceList.add(getRandomPrice())
        }
        return priceList
    }

    private fun getRandomPrice() = Random.nextDouble(999.99)
        .toBigDecimal()
        .setScale(2, RoundingMode.UP)
        .toDouble()
}
