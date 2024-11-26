package com.example.cryptext.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friend")
data class Friend (
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val sharedKey: String,
    val status: String = "Online"
)