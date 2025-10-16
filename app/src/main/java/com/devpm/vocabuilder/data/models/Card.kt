package com.devpm.vocabuilder.data.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "cards")
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var value: String,
    var transcription: String,
    var deckId: Int,
    var userId: Int,
    var created: Long
) {
}

@Dao
interface CardDao {

    @Insert
    suspend fun insertCard(card: Card)

    @Query("SELECT * FROM cards WHERE userId = :uid LIMIT 1")
    suspend fun getCardByUid(uid: Int): Card?

    @Query("SELECT * FROM cards WHERE id = :id LIMIT 1")
    suspend fun getCardById(id: Int): User?

    @Update
    suspend fun updateCard(card: Card)
}
