package com.itechart.turvo

import android.app.Application
import com.github.mikephil.charting.utils.Utils
import com.itechart.turvo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.mealty.di.serverModule

open class AppBase : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppBase)
            modules(listOf(appModule, serverModule))
        }
        Utils.init(this)
    }

}