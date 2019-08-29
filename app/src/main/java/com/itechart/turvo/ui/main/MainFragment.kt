package com.itechart.turvo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.itechart.turvo.R
import com.itechart.turvo.helper.hide
import com.itechart.turvo.ui.BaseFragment
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.editorActionEvents
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getBaseActivity()?.getToolbar()?.hide()

        val disposableTextChanges = et_ticker.textChanges()
            .skipInitialValue()
            .filter { it.isNotEmpty() }
            .doOnNext { showError(null) }
            .subscribe { viewModel.validateData(it.toString()) }

        val disposableClicks = Observable.merge(
            et_ticker.editorActionEvents()
                .filter { it.actionId == EditorInfo.IME_ACTION_DONE }
                .map { Unit },
            btn_next.clicks()
        )
            .doOnNext { getBaseActivity()?.hideKeyboard() }
            .subscribe { viewModel.onNext(et_ticker.text.toString()) }

        compositeDisposable.addAll(disposableTextChanges, disposableClicks)

        viewModel.formState.observe(this, Observer { state ->
            btn_next.isEnabled = state.isDataValid
            showError(state.tickerError)
        })

        viewModel.onNextResult.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { getBaseActivity()?.navigateToList(it) }
        })
    }

    private fun showError(error: Int?) {
        til_ticker.isErrorEnabled = error != null
        error?.let {
            til_ticker.error = getString(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
