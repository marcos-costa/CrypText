package com.example.cryptext.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    val name: String,
    val email: String,
    @PrimaryKey
    val username: String,
    val p: String? = null,
    val g: String? = null,
    val publicKey: String?= null,
)