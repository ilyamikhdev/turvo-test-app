package com.itechart.turvo.di

import com.itechart.turvo.BuildConfig
import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.repository.RepositoryDummyImpl
import com.itechart.turvo.repository.RepositoryImpl
import com.itechart.turvo.ui.details.DetailsViewModel
import com.itechart.turvo.ui.list.ListViewModel
import com.itechart.turvo.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single {
        when {
            BuildConfig.IS_DUMMY -> RepositoryDummyImpl()
            else -> RepositoryImpl(get())
        }
    }
    viewModel { (tickers: String) -> ListViewModel(tickers, get()) }
    viewModel { (item: Ticker) -> DetailsViewModel(item) }
    viewModel { MainViewModel() }
}