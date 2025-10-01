package com.devpm.vocabuilder

import android.app.Application
import androidx.room.Room
import com.devpm.vocabuilder.data.AppDb
import com.devpm.vocabuilder.data.models.User

class App: Application() {
    lateinit var db: AppDb
        private set

    var user: User? = null

    override fun onCreate() {
        super.onCreate()

        db = Room
            .databaseBuilder(applicationContext, AppDb::class.java, "app_db")
            .fallbackToDestructiveMigration(false)
            .build()
    }
}
