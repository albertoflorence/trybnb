package com.betrybe.trybnb.data.api

import com.betrybe.trybnb.data.models.AuthRequest
import com.betrybe.trybnb.data.models.AuthResponse
import com.betrybe.trybnb.data.models.BookingCreateResponse
import com.betrybe.trybnb.data.models.BookingDetails
import com.betrybe.trybnb.data.models.BookingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth")
    suspend fun auth(@Body request: AuthRequest): Response<AuthResponse>
    @GET("booking")
    suspend fun booking():Response<List<BookingResponse>>
    @GET("booking/{id}")
    suspend fun booking(@Path("id") id: String):Response<BookingDetails>
    @POST("booking")
    suspend fun createBooking(@Body request: BookingDetails):Response<BookingCreateResponse>
}