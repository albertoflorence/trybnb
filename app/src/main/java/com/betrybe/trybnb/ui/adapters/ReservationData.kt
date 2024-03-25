package com.betrybe.trybnb.ui.adapters

data class ReservationData (
    val name: String,
    val checkIn: String,
    val checkOut: String,
    val additionalNeeds: String,
    val totalPrice: Double,
    val isDepositPaid: Boolean
)