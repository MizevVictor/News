package com.victor.testapp

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
    }
}
