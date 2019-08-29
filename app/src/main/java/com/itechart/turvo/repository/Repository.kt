package com.itechart.turvo.repository


import android.util.Log
import com.itechart.turvo.BuildConfig
import com.itechart.turvo.entity.AlphaVantage
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.network.ApiServer
import java.math.RoundingMode
import java.util.*


interface Repository {
    suspend fun getData(tickers: String? = null): List<Ticker>
}

class RepositoryImpl(private val api: ApiServer) : Repository {
    companion object {
        const val sizeData: Int = 10
    }

    override suspend fun getData(tickers: String?): List<Ticker> =
        if (BuildConfig.IS_DUMMY) {
            DummyContent(parseTickers(tickers), sizeData).tickersList
        } else {
            val tickersList: MutableList<Ticker> = ArrayList()
            parseTickers(tickers).forEachIndexed { index, element ->
                val alpha = getQuote(element)
                tickersList.add(
                    Ticker(
                        id = index,
                        name = element,
                        prices = alpha?.timeSeries?.entries
                            ?.take(sizeData)
                            ?.reversed()
                            ?.map {
                                it.value.price.toBigDecimal()
                                    .setScale(2, RoundingMode.UP)
                                    .toDouble()
                            }
                            ?.toList()
                            .orEmpty()
                    )
                )
            }
            tickersList
        }


    private suspend fun getQuote(tickerName: String): AlphaVantage? {
        var alpha: AlphaVantage? = null
        try {
            val response = api.getQuote(tickerName)
            if (response.isSuccessful) {
                alpha = response.body()
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString())
        }
        return alpha
    }

    private fun parseTickers(tickers: String?): List<String> = tickers
        .orEmpty()
        .split(",")
        .map { it.trim() }
        .filterNot { it.isEmpty() }
}

