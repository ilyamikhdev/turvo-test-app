package com.itechart.turvo.ui.detail

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.ui.list.ListItemTicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sign

class DetailsViewModel(item: Ticker) : ViewModel() {
    private val _form = MutableLiveData<ListItemTicker>()
    val formState: LiveData<ListItemTicker> = _form

    init {
        viewModelScope.launch {
            _form.value = ListItemTicker(
                item = item,
                id = item.id,
                title = item.ticker,
                price = item.prices.lastOrNull()?.toString() ?: "",
                dataSets = getChartDataSets(item.prices)
            )
        }
    }

    private suspend fun getChartDataSets(prices: List<Double>) = withContext(Dispatchers.IO) {
        val values = prices.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val dataSets = java.util.ArrayList<ILineDataSet>()

        val set1 = LineDataSet(values, "10-day Prices").apply {
            color = Color.BLACK
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            valueTextSize = 9f
        }
        dataSets.add(set1)

        if (values.isNotEmpty()) {
            val set2 =
                LineDataSet(
                    values.subList(values.size - 1, values.size),
                    "Current price"
                ).apply {
                    color = Color.BLACK
                    setCircleColor(Color.BLACK)
                    circleRadius = 3f
                    setDrawCircleHole(false)
                    setDrawCircles(true)
                    setDrawValues(false)
                }
            dataSets.add(set2)
        }

        var indexFirst = 0
        var indexSecond = 0
        var diffMax = 0.0

        prices.forEachIndexed { index, first ->
            if (index != prices.size - 1) {
                val second = prices[index + 1]

                var diff = first - second
                if (diff.sign == -1.0) {
                    diff *= -1
                }

                if (diff > diffMax) {
                    diffMax = diff
                    indexFirst = index
                    indexSecond = index + 1
                }
            }
        }

        val set3 = LineDataSet(
            values.subList(indexFirst, indexSecond + 1),
            "Biggest jump"
        ).apply {
            color = Color.RED
            lineWidth = 1f
            setDrawCircles(false)
            setDrawValues(false)
        }

        dataSets.add(set3)

        dataSets
    }
}
