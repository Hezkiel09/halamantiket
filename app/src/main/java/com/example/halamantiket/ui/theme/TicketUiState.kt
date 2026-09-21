package com.example.halamantiket

data class TicketUiState(
    val ticketPrice: Int = 2500,
    val ticketCount: Int = 1
) {
    val totalPrice: Int get() = ticketCount * ticketPrice
}

