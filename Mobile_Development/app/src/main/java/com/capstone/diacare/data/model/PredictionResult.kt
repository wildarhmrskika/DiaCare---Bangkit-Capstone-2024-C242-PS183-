package com.capstone.diacare.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionResult(

    @field:SerializedName("fileName")
    val fileName: String? = null,

    @field:SerializedName("prediction")
    val prediction: Prediction? = null
): Parcelable

@Parcelize
data class Prediction(

    @field:SerializedName("confidence")
    val confidence: Double? = null,

    @field:SerializedName("class")
    val jsonMemberClass: String? = null
) : Parcelable