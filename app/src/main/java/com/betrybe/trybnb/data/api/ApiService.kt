package com.betrybe.trybnb.data.api

import com.betrybe.trybnb.data.models.AuthRequest
import com.betrybe.trybnb.data.models.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth")
    suspend fun auth(@Body request: AuthRequest): Response<AuthResponse>
}