package com.example.cryptext.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cryptext.data.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)

    @Query("SELECT * FROM message WHERE friend = :friendUserName AND timestamp = :time")
    fun getMessage(friendUserName: String, time: Long): Flow<Message>

    @Query("SELECT * FROM message")
    fun getAllMessage(): Flow<List<Message>>

    @Query("SELECT COUNT(*) FROM message WHERE read = 0")
    fun getUnreadMessagesCount(): Flow<Int>

    @Query("SELECT * FROM message WHERE friend = :username ORDER BY timestamp DESC")
    fun getFriendMessages(username: String): Flow<List<Message>>

    @Query("""
        SELECT * FROM message
        WHERE timestamp IN (
            SELECT MAX(timestamp) FROM message
            GROUP BY friend
        )
        ORDER BY timestamp DESC
    """)
    fun getLastMessages(): Flow<List<Message>>
}