package com.devpm.vocabuilder.data.models

data class User(
    val login: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val birthDate: String? = null,
    val email: String? = null,
    val phone: String? = null,
) {
}
