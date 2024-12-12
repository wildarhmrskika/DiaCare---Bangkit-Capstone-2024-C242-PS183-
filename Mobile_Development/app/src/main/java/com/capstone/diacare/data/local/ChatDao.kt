package com.capstone.diacare.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.diacare.data.model.ChatEntity

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChat(data: ChatEntity)

    @Query("SELECT * FROM chat")
    suspend fun getAllChat(): List<ChatEntity>
}