package com.victor.testapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Akash on 02/07/20
 */

@Suppress("DEPRECATION")
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BaseApplication

        fun isNetworkConnected(): Boolean {
            val connectivityManager =
                instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}
