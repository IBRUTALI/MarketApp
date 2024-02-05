package com.ighorosipov.marketapp

import android.app.Application
import com.ighorosipov.marketapp.di.AppComponent
import com.ighorosipov.marketapp.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}