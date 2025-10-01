package com.devpm.vocabuilder.data.models

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Автогенерируемый ID
    var login: String,
    var password: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var birthDate: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var avatarUri: String? = null,
    var created: Long
) {
}

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): User?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): User?

    @Update
    suspend fun updateUser(user: User)
}
