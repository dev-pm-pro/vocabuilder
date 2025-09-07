package com.devpm.vocabuilder.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Автогенерируемый ID
    val login: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val created: Long
) {
}
