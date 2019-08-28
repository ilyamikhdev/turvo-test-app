package com.itechart.turvo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.itechart.turvo.R
import com.itechart.turvo.helper.afterTextChanged
import com.itechart.turvo.ui.BaseFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getBaseActivity()?.supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.formState.observe(this, Observer { state ->
            btn_next.isEnabled = state.isDataValid
            showError(state.tickerError)
        })

        et_ticker.apply {
            afterTextChanged {
                showError(null)
                viewModel.validateData(it, showError = true)
            }

            setOnFocusChangeListener { _, hasFocus ->
                val text = et_ticker.text.toString()
                if (!hasFocus && text.isNotEmpty()) {
                    viewModel.validateData(text)
                } else {
                    showError(null)
                }
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> onNext()
                }
                false
            }
        }

        viewModel.onNextResult.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { getBaseActivity()?.navigateToList(it) }
        })

        btn_next.setOnClickListener { onNext() }

    }

    private fun showError(error: Int?) {
        til_ticker.isErrorEnabled = error != null
        error?.let {
            til_ticker.error = getString(it)
        }
    }

    private fun onNext() {
        viewModel.onNext(et_ticker.text.toString())
    }


}
