package com.itechart.turvo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itechart.turvo.R
import com.itechart.turvo.helper.Event

class MainViewModel : ViewModel() {
    private val _form = MutableLiveData<MainFormState>()
    val formState: LiveData<MainFormState> = _form

    private val _onNextResult = MutableLiveData<Event<String>>()
    val onNextResult: LiveData<Event<String>> = _onNextResult

    fun onNext(tickers: String) {
        if (validateData(tickers)) {
            _onNextResult.value = Event(tickers)
        }
    }

    fun validateData(tickers: String, showError: Boolean = true): Boolean {
        var state = when {
            tickers.isEmpty() -> {
                MainFormState(if (showError) R.string.main_error_empty else null)
            }
            tickers.split(",").map { it.trim() }.filterNot { it.isEmpty() }.size > 5 -> {
                MainFormState(if (showError) R.string.main_error_limit else null)
            }
            else -> MainFormState(isDataValid = true)
        }
        _form.value = state
        return state.isDataValid
    }
}
