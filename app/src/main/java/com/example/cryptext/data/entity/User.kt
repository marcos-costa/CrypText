package com.example.cryptext.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val username: String,
    val p: String?,
    val q: String?,
    val publicKey: String?,
)