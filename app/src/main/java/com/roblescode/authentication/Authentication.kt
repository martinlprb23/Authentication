package com.roblescode.authentication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Authentication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}