package com.betrybe.trybnb.data.models

data class BookingDetails (
    val firstname: String,
    val lastname: String,
    val totalprice: Double,
    val depositpaid: Boolean,
    val bookingdates: BookingDates,
    val additionalneeds: String
)

data class BookingDates (
    val checkin: String,
    val checkout: String
)