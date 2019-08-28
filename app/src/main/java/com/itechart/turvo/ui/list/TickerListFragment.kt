package com.itechart.turvo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.itechart.turvo.R
import com.itechart.turvo.helper.DefaultTransition
import com.itechart.turvo.helper.Transaction
import com.itechart.turvo.helper.addSharedElementTransition
import com.itechart.turvo.helper.changeTo
import com.itechart.turvo.ui.BaseFragment
import com.itechart.turvo.ui.detail.DetailsFragment
import com.itechart.turvo.ui.list.dummy.DummyContent
import com.itechart.turvo.ui.list.dummy.DummyContent.DummyItem


class TickerListFragment : BaseFragment() {
    private var argTickers: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        argTickers = arguments?.getString(ARG_TICKERS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ticker_list, container, false)

        val onItemClick = object : OnListFragmentInteractionListener {
            override fun onListFragmentInteraction(
                item: DummyItem?,
                sharedViews: Map<String, View>
            ) {
                getBaseActivity()?.changeTo(
                    fragment = DetailsFragment.newInstance(item).addSharedElementTransition(
                        DefaultTransition(),
                        Fade(),
                        this@TickerListFragment
                    ),
                    withBack = true,
                    transaction = Transaction.NONE,
                    sharedViews = sharedViews
                )
            }
        }

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = TickerRecyclerViewAdapter(
                    DummyContent(argTickers).items,
                    onItemClick
                ).apply { setHasStableIds(true) }
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getBaseActivity()?.supportActionBar?.title = getString(R.string.ticker_list_title)
        getBaseActivity()?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getBaseActivity()?.supportActionBar?.show()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: DummyItem?, sharedViews: Map<String, View>)
    }

    companion object {
        const val ARG_TICKERS = "tickers"
        @JvmStatic
        fun newInstance(tickers: String) =
            TickerListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TICKERS, tickers)
                }
            }
    }
}
