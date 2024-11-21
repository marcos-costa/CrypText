package com.example.cryptext.data.ui

data class User (
    val name: String,
    val username: String,
    val email: String,
    val status: String = "Online"
)