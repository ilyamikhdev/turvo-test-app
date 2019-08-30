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
import com.itechart.turvo.entity.convertToListItem
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
        repository.getData(tickers)
            .map { it.convertToListItem(getChartDataSets(it.prices)) }
    }

    private fun getChartDataSets(prices: List<Double>): LineData? {
        if (prices.isEmpty()) {
            return null
        }

        val values = prices.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val set1 = LineDataSet(values, null).apply {
            color = Color.BLACK
            lineWidth = 1f
            setDrawCircleHole(false)
            setDrawCircles(false)
            setDrawValues(false)
        }

        val set2 = LineDataSet(values.subList(values.size - 1, values.size), null).apply {
            setCircleColor(Color.BLACK)
            circleRadius = 3f
            setDrawCircleHole(false)
            setDrawCircles(true)
            setDrawValues(false)
        }

        return LineData(mutableListOf<ILineDataSet>(set1, set2))
    }
}
