package com.itechart.turvo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.itechart.turvo.R
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.helper.hide
import com.itechart.turvo.helper.show
import com.itechart.turvo.ui.BaseFragment
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
        initToolbar()

        adapter = ListRvAdapter(listener = onItemClick).apply { setHasStableIds(true) }

        with(rv_list) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@ListFragment.adapter
        }

        viewModel.formState.observe(this, Observer {
            progress_list.hide()
            adapter.values = it
            adapter.notifyDataSetChanged()

        })
    }

    private fun initToolbar() {
        val colorWhite = ContextCompat.getColor(context!!, android.R.color.white)
        val colorPrimary = ContextCompat.getColor(context!!, R.color.colorPrimary)
        getBaseActivity()?.supportActionBar?.setHomeAsUpIndicator(null)
        getBaseActivity()?.getToolbar()?.show()
        getBaseActivity()?.getToolbar()?.apply {
            title = getString(R.string.title_list_tickers)
            setTitleTextColor(colorWhite)
            navigationIcon?.setTint(colorWhite)
            background?.setTint(colorPrimary)
        }
    }

    private val onItemClick = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: Ticker, sharedViews: Map<String, View>) {
            getBaseActivity()?.navigateToDetails(item, sharedViews, this@ListFragment)
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Ticker, sharedViews: Map<String, View>)
    }
}
