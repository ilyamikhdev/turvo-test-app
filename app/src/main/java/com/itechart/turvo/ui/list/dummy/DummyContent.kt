package com.itechart.turvo.ui.list.dummy

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.RoundingMode
import java.util.*
import kotlin.random.Random

/**
 * Helper class for providing sample ticker for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
class DummyContent(tickers: String?) {
    val items: MutableList<DummyItem> = ArrayList()

    init {
        tickers
            .orEmpty()
            .split(",")
            .map { it.trim() }
            .filterNot { it.isEmpty() }
            .forEachIndexed { index, element ->
                items.add(createDummyItem(index, element))
            }
    }

    private fun createDummyItem(position: Int, ticker: String) = DummyItem(
        id = position,
        ticker = ticker,
        prices = makePriceList()
    )

    private fun makePriceList(): List<Double> {
        val priceList = mutableListOf<Double>()
        repeat((0..10).count()) {
            priceList.add(getRandomPrice())
        }
        return priceList
    }

    private fun getRandomPrice() = Random.nextDouble(999.99)
        .toBigDecimal()
        .setScale(2, RoundingMode.UP)
        .toDouble()

    /**
     * A dummy item representing a piece of ticker.
     */
    @Parcelize
    data class DummyItem(
        val id: Int,
        val ticker: String,
        val prices: List<Double>
    ) : Parcelable
}
