package com.example.cryptext.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val friend: String,
    val timestamp: Long,
    val received: Boolean,
    val read: Boolean,
    val content: String,
)
