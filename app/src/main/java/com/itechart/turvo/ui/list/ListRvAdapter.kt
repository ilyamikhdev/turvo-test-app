package com.itechart.turvo.ui.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.initUI
import com.itechart.turvo.ui.details.DetailsFragment
import com.itechart.turvo.ui.list.ListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.item_ticker.view.*

class ListRvAdapter(
    var values: List<ListItemTicker> = emptyList(),
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ListRvAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = (v.tag as ListItemTicker).item
            val sharedViews = mapOf(
                v.item_ticker.transitionName to v.item_ticker,
                v.item_price.transitionName to v.item_price,
                v.item_chart.transitionName to v.item_chart,
                v.item_line.transitionName to v.item_line
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

        holder.chart.initUI()

        holder.chart.data = item.dataLines
        holder.ticker.text = item.title
        holder.price.text = item.price

        holder.ticker.transitionName = "${DetailsFragment.ARG_TICKER_TRANS_TICKER} ${item.id}"
        holder.price.transitionName = "${DetailsFragment.ARG_TICKER_TRANS_PRICE} ${item.id}"
        holder.chart.transitionName = "${DetailsFragment.ARG_TICKER_TRANS_CHART} ${item.id}"
        holder.line.transitionName = "${DetailsFragment.ARG_TICKER_TRANS_LINE} ${item.id}"

        with(holder.view) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = values.size
    override fun getItemId(position: Int): Long = values[position].id.toLong()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val ticker: TextView = view.item_ticker
        val price: TextView = view.item_price
        val chart: LineChart = view.item_chart
        val line: View = view.item_line
    }
}

data class ListItemTicker(
    val item: Ticker,
    val id: Int,
    val title: String,
    val price: String,
    val dataLines: LineData?
)
