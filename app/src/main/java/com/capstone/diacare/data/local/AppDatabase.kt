package com.capstone.diacare.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.diacare.data.model.ChatEntity
import com.capstone.diacare.data.model.PredictionEntity

@Database(entities = [PredictionEntity::class, ChatEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun predictionDao(): PredictionDao
    abstract fun chatDao() : ChatDao

}