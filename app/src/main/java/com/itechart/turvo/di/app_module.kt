package com.itechart.turvo.di

import com.itechart.turvo.repository.DummyContent
import com.itechart.turvo.ui.detail.DetailsViewModel
import com.itechart.turvo.ui.list.ListViewModel
import com.itechart.turvo.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { (tickers: String) -> ListViewModel(tickers) }
    viewModel { (item: DummyContent.DummyItem) -> DetailsViewModel(item) }
    viewModel { MainViewModel() }
}
