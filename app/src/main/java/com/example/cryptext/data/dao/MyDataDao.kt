package com.example.cryptext.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cryptext.data.entity.MyData
import com.example.cryptext.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: MyData)

    @Update
    fun update(data: MyData)

    @Query("SELECT * from mydata WHERE id = 0")
    fun getMyData(): MyData

    @Query("SELECT COUNT(*) FROM mydata")
    fun count(): Int
}