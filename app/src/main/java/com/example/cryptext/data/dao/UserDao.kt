package com.example.cryptext.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cryptext.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE username = :name")
    fun getUser(name: String): Flow<User>

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE publicKey IS NOT NULL")
    fun getAllReceivedSolicitations(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE publicKey IS NULL")
    fun getAllPendingSolicitations(): Flow<List<User>>

    @Query("SELECT COUNT(*) FROM user WHERE publicKey IS NOT NULL")
    fun getPendingSolicitatinonsCount(): Flow<Int>
}