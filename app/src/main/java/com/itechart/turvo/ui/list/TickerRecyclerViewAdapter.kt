package com.itechart.turvo.ui.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itechart.turvo.R
import com.itechart.turvo.ui.list.TickerListFragment.OnListFragmentInteractionListener
import com.itechart.turvo.ui.list.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_ticker.view.*

class TickerRecyclerViewAdapter(
    private val values: List<DummyItem>,
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TickerRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            listener?.onListFragmentInteraction(
                item, mapOf(
                    v.item_ticker.transitionName to v.item_ticker,
                    v.item_price.transitionName to v.item_price
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_ticker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.ticker.text = item.ticker
        holder.ticker.transitionName = "item_ticker ${item.id}"
        holder.price.text = item.prices.lastOrNull()?.toString() ?: ""
        holder.price.transitionName = "item_price ${item.id}"


        with(holder.mView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int = values.size
    override fun getItemId(position: Int): Long = values[position].id.toLong()

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val ticker: TextView = mView.item_ticker
        val price: TextView = mView.item_price
    }
}
