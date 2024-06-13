package com.bangkit.luminasense.backend.service.auth.model

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val password: String
)

