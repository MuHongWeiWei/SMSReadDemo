package com.fly.smsreaddemo

data class SMS(
    val id: Int,
    val sender: String,
    val body: String,
    val dateTime: String
)