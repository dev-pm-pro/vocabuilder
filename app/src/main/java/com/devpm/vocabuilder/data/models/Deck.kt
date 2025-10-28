package com.devpm.vocabuilder.data.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "decks")
data class Deck(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String,
    var userId: Int,
    var created: Long
) {
}

@Dao
interface DeckDao {

    @Insert
    suspend fun insertDeck(deck: Deck)

    @Query("SELECT * FROM decks WHERE id = :id LIMIT 1")
    suspend fun getDeckById(id: Int): Deck?

    @Query("SELECT * FROM decks WHERE userId = :uid")
    suspend fun getDecksByUid(uid: Int): List<Deck>

    @Update
    suspend fun updateDeck(deck: Deck)
}
