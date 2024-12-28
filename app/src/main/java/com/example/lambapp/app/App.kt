package com.example.lambapp.app

import android.app.Application
import com.example.lambapp.di.AppComponent
import com.example.lambapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        super.onCreate()
    }
}
