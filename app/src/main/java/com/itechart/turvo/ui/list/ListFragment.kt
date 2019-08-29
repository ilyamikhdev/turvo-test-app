package com.itechart.turvo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.*
import com.itechart.turvo.ui.BaseFragment
import com.itechart.turvo.ui.detail.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ListFragment : BaseFragment() {
    private var argTickers: String? = null
    private val viewModel: ListViewModel by viewModel { parametersOf(argTickers) }

    private lateinit var adapter: TickerRecyclerViewAdapter

    companion object {
        const val ARG_TICKERS = "tickers"
        fun newInstance(tickers: String) = ListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TICKERS, tickers)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argTickers = arguments?.getString(ARG_TICKERS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val onItemClick = object : OnListFragmentInteractionListener {
            override fun onListFragmentInteraction(
                item: Ticker,
                sharedViews: Map<String, View>
            ) {

                getBaseActivity()?.changeTo(
                    fragment = DetailsFragment.newInstance(item).addSharedElementTransition(
                        DefaultTransition(),
                        Fade(),
                        this@ListFragment
                    ),
                    withBack = true,
                    transaction = Transaction.NONE,
                    sharedViews = sharedViews
                )
            }
        }

        if (view is RecyclerView) {
            adapter = TickerRecyclerViewAdapter(emptyList(), onItemClick)
                .apply { setHasStableIds(true) }
            with(view) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = this@ListFragment.adapter
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val colorWhite = ContextCompat.getColor(context!!, android.R.color.white)
        val colorPrimary = ContextCompat.getColor(context!!, R.color.colorPrimary)
        getBaseActivity()?.getToolbar()?.show()
        getBaseActivity()?.getToolbar()?.apply {
            title = getString(R.string.ticker_list_title)
            setTitleTextColor(colorWhite)
            navigationIcon?.setTint(colorWhite)
            background?.setTint(colorPrimary)
        }
        getBaseActivity()?.supportActionBar?.setHomeAsUpIndicator(null)

        viewModel.formState.observe(this, Observer {
            adapter.values = it
            adapter.notifyDataSetChanged()

        })
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Ticker, sharedViews: Map<String, View>)
    }
}
