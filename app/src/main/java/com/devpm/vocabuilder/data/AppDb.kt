package com.devpm.vocabuilder.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devpm.vocabuilder.data.models.Card
import com.devpm.vocabuilder.data.models.CardDao
import com.devpm.vocabuilder.data.models.Deck
import com.devpm.vocabuilder.data.models.DeckDao
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.data.models.UserDao

@Database(entities = [User::class, Card::class, Deck::class], version = 4)
abstract class AppDb : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun deckDao(): DeckDao
    abstract fun userDao(): UserDao
}
