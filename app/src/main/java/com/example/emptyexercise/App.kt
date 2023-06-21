package com.example.emptyexercise

import android.app.Application
import coil.Coil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Coil.imageLoader(this)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}