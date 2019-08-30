package com.itechart.turvo.ui.list

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvo.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val tickers: String, private val repository: Repository) : ViewModel() {
    private val _form = MutableLiveData<List<ListItemTicker>>()
    val formState: LiveData<List<ListItemTicker>> = _form

    init {
        viewModelScope.launch {
            _form.value = loadData()
        }
    }

    private suspend fun loadData() = withContext(Dispatchers.IO) {
        repository.getData(tickers).map {
            ListItemTicker(
                item = it,
                id = it.id,
                title = it.name,
                price = it.prices.lastOrNull()?.toString() ?: "",
                dataLines = getChartDataSets(it.prices)
            )
        }
    }

    private fun getChartDataSets(prices: List<Double>): LineData {
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

        return LineData(dataSets)
    }
}
