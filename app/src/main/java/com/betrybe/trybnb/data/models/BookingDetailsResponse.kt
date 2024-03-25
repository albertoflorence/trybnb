package com.betrybe.trybnb.data.models

data class BookingDetailsResponse (
    val firstName: String,
    val lastName: String,
    val totalPrice: Double,
    val depositPaid: Boolean,
    val bookingDates: BookingDates,
    val additionalNeeds: String
)

data class BookingDates (
    val checkin: String,
    val checkout: String
)