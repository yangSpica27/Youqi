package com.spica.app

import android.app.Application
import android.content.Context
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.spica.app.tools.widget.WebViewPool
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*


@HiltAndroidApp
class App : Application() {

    companion object {
        private lateinit var application: Context
        val appContext: Context
            get() {
                return application
            }
    }

    override fun onCreate() {
        super.onCreate()
        // 初始化AppCenter
        CoroutineScope(Dispatchers.Default).launch {
            AppCenter.start(
                this@App,
                "881ec5a2-26f1-4ff4-accf-2950eea4920e",
                Analytics::class.java, Crashes::class.java
            )
        }
        WebViewPool.init(this)
        application = this
    }
}