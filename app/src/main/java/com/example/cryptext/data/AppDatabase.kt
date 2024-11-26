package com.example.cryptext.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptext.data.dao.FriendDao
import com.example.cryptext.data.dao.MessageDao
import com.example.cryptext.data.dao.UserDao
import com.example.cryptext.data.entity.Friend
import com.example.cryptext.data.entity.Message
import com.example.cryptext.data.entity.User

@Database(entities = [
    User::class,
    Friend::class,
    Message::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun friendDao(): FriendDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}


