package com.itechart.turvo.di

import com.itechart.turvo.entity.Ticker
import com.itechart.turvo.repository.Repository
import com.itechart.turvo.repository.RepositoryImpl
import com.itechart.turvo.ui.detail.DetailsViewModel
import com.itechart.turvo.ui.list.ListViewModel
import com.itechart.turvo.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<Repository> { RepositoryImpl(get()) }
    viewModel { (tickers: String) -> ListViewModel(tickers, get()) }
    viewModel { (item: Ticker) -> DetailsViewModel(item) }
    viewModel { MainViewModel() }
}
