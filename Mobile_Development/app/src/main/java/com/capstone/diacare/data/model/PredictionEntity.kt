package com.capstone.diacare.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("prediction_history")
data class PredictionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("result")
    val result: String,

    @ColumnInfo("confidence")
    val confidence: Double,

    @ColumnInfo("date")
    val date: String,

    @ColumnInfo("image_uri")
    val imageUri: String,


    )
