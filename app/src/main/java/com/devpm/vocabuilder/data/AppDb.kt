package com.devpm.vocabuilder.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devpm.vocabuilder.data.models.Card
import com.devpm.vocabuilder.data.models.CardDao
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.data.models.UserDao

@Database(entities = [User::class, Card::class], version = 3)
abstract class AppDb : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun userDao(): UserDao
}
