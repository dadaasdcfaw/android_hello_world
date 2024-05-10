package com.example.api


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class random_app_payload(
    val max_value: String,
    val random_value: String,
    val timestamp: String =
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
)