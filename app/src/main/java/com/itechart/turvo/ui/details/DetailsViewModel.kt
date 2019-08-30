package com.itechart.turvo.ui.details

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.entity.convertToListItem
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
            _form.value = item.convertToListItem(getChartDataSets(item.prices))
        }
    }

    private suspend fun getChartDataSets(prices: List<Double>) = withContext(Dispatchers.IO) {
        var result: LineData? = null

        if (prices.isNotEmpty()) {
            val values = prices.mapIndexed { index, value ->
                Entry(index.toFloat(), value.toFloat())
            }

            val set1 = LineDataSet(values, null).apply {
                color = Color.BLACK
                lineWidth = 2f
                setDrawCircles(false)
                setDrawValues(false)
                valueTextSize = 9f
            }

            val set2 = LineDataSet(values.subList(values.size - 1, values.size), null).apply {
                color = Color.BLACK
                setCircleColor(Color.BLACK)
                circleRadius = 3f
                setDrawCircleHole(false)
                setDrawCircles(true)
                setDrawValues(false)
            }

            var indexFirst = 0
            var indexSecond = 0
            var diffMax = 0.0f

            values.forEachIndexed { index, first ->
                if (index != values.size - 1) {
                    val second = values[index + 1]

                    var diff = first.y - second.y
                    if (diff.sign == -1.0f) {
                        diff *= -1
                    }

                    if (diff > diffMax) {
                        diffMax = diff
                        indexFirst = index
                        indexSecond = index + 1
                    }
                }
            }

            val set3 = LineDataSet(values.subList(indexFirst, indexSecond + 1), null).apply {
                color = Color.RED
                lineWidth = 2f
                setDrawCircles(false)
                setDrawValues(false)
            }

            result = LineData(mutableListOf<ILineDataSet>(set1, set2, set3))
        }

        result
    }
}
