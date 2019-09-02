package com.itechart.turvo.repository


import android.util.Log
import com.itechart.turvo.entity.AlphaVantage
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.parseTickers
import com.itechart.turvo.helper.round
import com.itechart.turvo.network.ApiServer


interface Repository {
    suspend fun getData(tickers: String?, days: Int = 10): List<Ticker>
}

class RepositoryImpl(private val api: ApiServer) : Repository {

    override suspend fun getData(tickers: String?, days: Int) =
        parseTickers(tickers).mapIndexed { index, element ->
            val alpha = getQuote(element)
            Ticker(
                id = index,
                name = element,
                prices = alpha?.timeSeries?.entries
                    ?.take(days)
                    ?.reversed()
                    ?.map { it.value.price.round() }
                    ?.toList()
                    .orEmpty()
            )
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
}

