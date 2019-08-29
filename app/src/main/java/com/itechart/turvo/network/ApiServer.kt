package com.itechart.turvo.network

import com.itechart.turvo.BuildConfig
import com.itechart.turvo.entity.AlphaVantage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServer {
    //https://www.alphavantage.co/query?apikey=13MFUGNDCDVA0ISN&function=TIME_SERIES_DAILY_ADJUSTED&symbol=GOOG
    @GET("query")
    suspend fun getQuote(
        @Query("symbol") symbol: String,
        @Query("function") function: String = "TIME_SERIES_DAILY_ADJUSTED",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): Response<AlphaVantage>
}