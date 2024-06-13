package com.bangkit.luminasense.backend.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
)

data class RegisterResponse(
    val message: String
)

interface ApiService {
    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}
