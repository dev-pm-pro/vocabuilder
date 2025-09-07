package com.devpm.vocabuilder

import android.app.Application
import androidx.room.Room
import com.devpm.vocabuilder.data.AppDb

class App: Application() {
    lateinit var db: AppDb
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(applicationContext, AppDb::class.java, "app_db").build()
    }
}
