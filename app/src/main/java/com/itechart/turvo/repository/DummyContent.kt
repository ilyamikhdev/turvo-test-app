package com.itechart.turvo.repository

import com.itechart.turvo.entity.Ticker
import java.math.RoundingMode
import java.util.*
import kotlin.random.Random

/**
 * Helper class for providing sample ticker for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
class DummyContent(tickers: String?, private var countDays: Int = 10) {
    val tickersList: MutableList<Ticker> = ArrayList()

    init {
        tickers
            .orEmpty()
            .split(",")
            .map { it.trim() }
            .filterNot { it.isEmpty() }
            .forEachIndexed { index, element ->
                tickersList.add(createDummyItem(index, element))
            }
    }

    private fun createDummyItem(position: Int, ticker: String) = Ticker(
        id = position,
        ticker = ticker,
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
