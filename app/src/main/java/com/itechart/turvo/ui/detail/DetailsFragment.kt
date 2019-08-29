package com.itechart.turvo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.itechart.turvo.R
import com.itechart.turvo.helper.show
import com.itechart.turvo.ui.BaseFragment
import com.itechart.turvo.ui.list.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailsFragment : BaseFragment() {

    private var argItem: DummyContent.DummyItem? = null

    companion object {
        const val ARG_TICKER_ITEM = "ticker_item"
        const val ARG_TICKER_TRANS_TICKER = "ticker_name"
        const val ARG_TICKER_TRANS_PRICE = "price_name"
        const val ARG_TICKER_TRANS_CHART = "chart_name"

        fun newInstance(item: DummyContent.DummyItem?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_TICKER_ITEM, item)
                putString(ARG_TICKER_TRANS_TICKER, "item_ticker ${item?.id}")
                putString(ARG_TICKER_TRANS_PRICE, "item_price ${item?.id}")
                putString(ARG_TICKER_TRANS_CHART, "item_chart ${item?.id}")
            }
        }
    }

    private val viewModel: DetailsViewModel by viewModel { parametersOf(argItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        argItem = arguments?.getParcelable(ARG_TICKER_ITEM)
        return inflater.inflate(R.layout.fragment_details, container, false).apply {
            findViewById<TextView>(R.id.tv_details_ticker).transitionName =
                arguments?.getString(ARG_TICKER_TRANS_TICKER)
            findViewById<TextView>(R.id.tv_details_price).transitionName =
                arguments?.getString(ARG_TICKER_TRANS_PRICE)
            findViewById<LineChart>(R.id.chart_details).transitionName =
                arguments?.getString(ARG_TICKER_TRANS_CHART)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val colorWhite = ContextCompat.getColor(context!!, android.R.color.white)
        getBaseActivity()?.getToolbar()?.show()
        getBaseActivity()?.getToolbar()?.apply {
            title = null
            navigationIcon?.setTint(colorWhite)
            background?.setTint(colorWhite)
        }
        getBaseActivity()?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)

        chart_details.axisLeft.isEnabled = false
        chart_details.axisLeft.spaceTop = 40f
        chart_details.axisLeft.spaceBottom = 40f
        chart_details.axisRight.isEnabled = false
        chart_details.xAxis.isEnabled = false
        chart_details.description.isEnabled = false
        chart_details.legend.isEnabled = false
        chart_details.setTouchEnabled(false)

        viewModel.formState.observe(this, Observer {
            tv_details_ticker.text = it.title
            tv_details_price.text = it.price
            chart_details.data = LineData(it.dataSets)
        })

    }
}
