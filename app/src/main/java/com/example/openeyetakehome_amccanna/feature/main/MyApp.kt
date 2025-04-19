package com.example.openeyetakehome_amccanna.feature.main

import android.app.Application
import com.example.openeyetakehome_amccanna.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}