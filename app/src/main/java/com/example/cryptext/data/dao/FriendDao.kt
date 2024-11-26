package com.example.cryptext.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cryptext.data.entity.Friend
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(friend: Friend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(friends: List<Friend>)

    @Update
    fun update(friend: Friend)

    @Delete
    fun delete(friend: Friend)

    @Query("SELECT * FROM friend WHERE username = :name")
    fun getFriend(name: String): Flow<Friend>

    @Query("SELECT * FROM friend")
    fun getAllFriend(): Flow<List<Friend>>
}
