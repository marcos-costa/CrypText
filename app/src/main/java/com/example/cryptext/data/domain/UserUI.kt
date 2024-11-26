package com.example.cryptext.data.domain

data class UserUI (
    val name: String,
    val username: String,
    val email: String,
    val status: String = "Online"
)