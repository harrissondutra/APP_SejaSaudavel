package com.estudo.app_dietacheck

import android.app.Application
import com.estudo.app_dietacheck.models.AppDatabase

class App : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}