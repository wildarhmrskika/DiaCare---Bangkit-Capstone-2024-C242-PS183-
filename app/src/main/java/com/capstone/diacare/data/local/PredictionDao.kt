package com.capstone.diacare.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.diacare.data.model.PredictionEntity

@Dao
interface PredictionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrediction(data : PredictionEntity)

    @Query("SELECT * FROM prediction_history")
    suspend fun getAllHistory() : List<PredictionEntity>

    @Query("SELECT * FROM prediction_history WHERE id = :id LIMIT 1")
    suspend fun getHistoryById(id : Int) : PredictionEntity

    @Delete
    suspend fun deleteItem(data: PredictionEntity)
}