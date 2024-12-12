package com.capstone.diacare.data.model

import com.google.gson.annotations.SerializedName

data class PredictionTestServerResult(

	@field:SerializedName("model_status")
	val modelStatus: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
