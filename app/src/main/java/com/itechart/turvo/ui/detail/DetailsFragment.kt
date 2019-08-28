package com.itechart.turvo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.itechart.turvo.R
import com.itechart.turvo.ui.BaseFragment
import com.itechart.turvo.ui.list.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment() : BaseFragment() {

    private var argItem: DummyContent.DummyItem? = null

    companion object {
        const val ARG_TICKER_ITEM = "ticker_item"
        const val ARG_TICKER_TRANSITION_NAME_TICKER = "ticker_trans_name"
        const val ARG_TICKER_TRANSITION_NAME_PRICE = "ticker_price_name"
        fun newInstance(item: DummyContent.DummyItem?) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_TICKER_ITEM, item)
                putString(ARG_TICKER_TRANSITION_NAME_TICKER, "item_ticker ${item?.id}")
                putString(ARG_TICKER_TRANSITION_NAME_PRICE, "item_price ${item?.id}")
            }
        }
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        argItem = arguments?.getParcelable(ARG_TICKER_ITEM)
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        view.findViewById<TextView>(R.id.tv_details_ticker).transitionName =
            arguments?.getString(ARG_TICKER_TRANSITION_NAME_TICKER)
        view.findViewById<TextView>(R.id.tv_details_price).transitionName =
            arguments?.getString(ARG_TICKER_TRANSITION_NAME_PRICE)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel

        getBaseActivity()?.supportActionBar?.title = getString(R.string.ticker_details_title)
        getBaseActivity()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getBaseActivity()?.supportActionBar?.show()

        tv_details_ticker.text = argItem?.ticker
        tv_details_price.text = argItem?.prices?.lastOrNull().toString()
    }

}
