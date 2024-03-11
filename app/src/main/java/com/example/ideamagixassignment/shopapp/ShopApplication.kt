package com.example.ideamagixassignment.shopapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}