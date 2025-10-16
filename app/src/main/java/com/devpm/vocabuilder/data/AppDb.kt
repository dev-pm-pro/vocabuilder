package com.devpm.vocabuilder.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.data.models.UserDao

@Database(entities = [User::class], version = 2)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}
