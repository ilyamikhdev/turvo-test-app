package com.itechart.turvo.ui.list

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvo.ui.list.dummy.DummyContent

class ListViewModel(tickers: String) : ViewModel() {
    private val _form = MutableLiveData<List<ListItemTicker>>()
    val formState: LiveData<List<ListItemTicker>> = _form


    init {
        _form.value = DummyContent(tickers).items.map {
            ListItemTicker(
                item = it,
                id = it.id,
                title = it.ticker,
                price = it.prices.lastOrNull()?.toString() ?: "",
                dataSets = getChartDataSets(it.prices)
            )
        }
    }

    private fun getChartDataSets(prices: List<Double>): ArrayList<ILineDataSet> {
        val values = prices.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val dataSets = ArrayList<ILineDataSet>()

        val set1 = LineDataSet(values, null).apply {
            color = Color.BLACK
            lineWidth = 1f
            setDrawCircleHole(false)
            setDrawCircles(false)
            setDrawValues(false)
        }
        dataSets.add(set1)

        if (values.isNotEmpty()) {
            val set2 = LineDataSet(values.subList(values.size - 1, values.size), null).apply {
                setCircleColor(Color.BLACK)
                circleRadius = 3f
                setDrawCircleHole(false)
                setDrawCircles(true)
                setDrawValues(false)
            }
            dataSets.add(set2)
        }

        return dataSets
    }
}