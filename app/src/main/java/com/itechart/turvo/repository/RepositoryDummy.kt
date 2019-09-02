package com.itechart.turvo.repository


import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.parseTickers
import com.itechart.turvo.helper.round
import kotlin.random.Random

class RepositoryDummyImpl : Repository {

    override suspend fun getData(tickers: String?, days: Int) =
        parseTickers(tickers).mapIndexed { index, element ->
            Ticker(
                id = index,
                name = element,
                prices = makePriceList(days)
            )
        }

    private fun makePriceList(days: Int) = (0 until days).map { Random.nextDouble(999.99).round() }
}