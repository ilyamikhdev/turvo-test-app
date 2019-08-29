package com.itechart.turvo.ui.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.ui.list.ListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.item_ticker.view.*

class TickerRecyclerViewAdapter(
    var values: List<ListItemTicker>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TickerRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = (v.tag as ListItemTicker).item
            val sharedViews = mapOf(
                v.item_ticker.transitionName to v.item_ticker,
                v.item_price.transitionName to v.item_price,
                v.item_chart.transitionName to v.item_chart
            )
            listener?.onListFragmentInteraction(item, sharedViews)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.ticker.text = item.title
        holder.price.text = item.price
        initChart(holder.chart, item.dataSets)

        holder.ticker.transitionName = "item_ticker ${item.id}"
        holder.price.transitionName = "item_price ${item.id}"
        holder.chart.transitionName = "item_chart ${item.id}"

        with(holder.view) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = values.size
    override fun getItemId(position: Int): Long = values[position].id.toLong()

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ticker: TextView = view.item_ticker
        val price: TextView = view.item_price
        val chart: LineChart = view.item_chart
    }

    private fun initChart(chart_details: LineChart, dataSets: ArrayList<ILineDataSet>) {
        chart_details.data = LineData(dataSets)
        chart_details.axisLeft.isEnabled = false
        chart_details.axisLeft.spaceTop = 40f
        chart_details.axisLeft.spaceBottom = 40f
        chart_details.axisRight.isEnabled = false
        chart_details.xAxis.isEnabled = false
        chart_details.description.isEnabled = false
        chart_details.legend.isEnabled = false
        chart_details.setTouchEnabled(false)
    }
}

data class ListItemTicker(
    val item: Ticker,
    val id: Int,
    val title: String,
    val price: String,
    val dataSets: ArrayList<ILineDataSet>
)
