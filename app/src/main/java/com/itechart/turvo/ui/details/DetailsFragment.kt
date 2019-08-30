package com.itechart.turvo.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.initUI
import com.itechart.turvo.helper.show
import com.itechart.turvo.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailsFragment : BaseFragment() {

    private var argItem: Ticker? = null
    private val viewModel: DetailsViewModel by viewModel { parametersOf(argItem) }

    companion object {
        const val ARG_TICKER_ITEM = "ticker_item"
        const val ARG_TICKER_TRANS_TICKER = "ticker_name"
        const val ARG_TICKER_TRANS_PRICE = "price_name"
        const val ARG_TICKER_TRANS_CHART = "chart_name"
        const val ARG_TICKER_TRANS_LINE = "line_name"

        fun newInstance(item: Ticker?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_TICKER_ITEM, item)
                putString(ARG_TICKER_TRANS_TICKER, "$ARG_TICKER_TRANS_TICKER ${item?.id}")
                putString(ARG_TICKER_TRANS_PRICE, "$ARG_TICKER_TRANS_PRICE ${item?.id}")
                putString(ARG_TICKER_TRANS_CHART, "$ARG_TICKER_TRANS_CHART ${item?.id}")
                putString(ARG_TICKER_TRANS_LINE, "$ARG_TICKER_TRANS_LINE ${item?.id}")
            }
        }
    }

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
            findViewById<View>(R.id.chart_line).transitionName =
                arguments?.getString(ARG_TICKER_TRANS_LINE)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initToolbar()
        chart_details.initUI()

        viewModel.formState.observe(this, Observer {
            tv_details_ticker.text = it.title
            tv_details_price.text = it.price
            chart_details.data = it.dataLines
        })
    }

    private fun initToolbar() {
        val colorWhite = ContextCompat.getColor(context!!, android.R.color.white)
        val colorBlack = ContextCompat.getColor(context!!, android.R.color.black)
        getBaseActivity()?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        getBaseActivity()?.getToolbar()?.show()
        getBaseActivity()?.getToolbar()?.apply {
            title = null
            navigationIcon?.setTint(colorBlack)
            background?.setTint(colorWhite)
        }
    }
}
