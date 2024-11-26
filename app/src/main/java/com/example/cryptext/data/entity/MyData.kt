package com.example.cryptext.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "mydata")
data class MyData(
    @PrimaryKey
    val id: Int = 0,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val privateKey: Long,
    )
