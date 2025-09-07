package com.devpm.vocabuilder

import android.app.Application
import androidx.room.Room
import com.devpm.vocabuilder.data.AppDb

class App: Application() {
    val db = Room.databaseBuilder(
        applicationContext,
        AppDb::class.java,
        "app_db"
    ).build()

    override fun onCreate() {
        super.onCreate()
        // Инициализация глобальных компонентов приложения
    }
}
