package com.morse.valu.app

import com.expertapps.base.extensions.toast

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->  throwable.message?.toast(applicationContext) }
    }
}