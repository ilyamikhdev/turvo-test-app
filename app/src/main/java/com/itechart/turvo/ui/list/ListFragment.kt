package com.itechart.turvo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.*
import com.itechart.turvo.ui.BaseFragment
import com.itechart.turvo.ui.detail.DetailsFragment
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ListFragment : BaseFragment() {
    private var argTickers: String? = null
    private val viewModel: ListViewModel by viewModel { parametersOf(argTickers) }

    private lateinit var adapter: ListRvAdapter

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
        return inflater.inflate(R.layout.fragment_list, container, false)
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

        adapter = ListRvAdapter(emptyList(), onItemClick).apply { setHasStableIds(true) }
        with(rv_list) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@ListFragment.adapter
        }

        viewModel.formState.observe(this, Observer {
            progress.hide()
            adapter.values = it
            adapter.notifyDataSetChanged()

        })
    }

    private val onItemClick = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: Ticker, sharedViews: Map<String, View>) {
            val fragment = DetailsFragment.newInstance(item).addSharedElementTransition(
                DefaultTransition(), Fade(), this@ListFragment
            )
            getBaseActivity()?.changeTo(
                fragment = fragment,
                withBack = true,
                transaction = Transaction.NONE,
                sharedViews = sharedViews
            )
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Ticker, sharedViews: Map<String, View>)
    }
}
