package edu.iesam.bikerly.data.remote.api

import com.google.gson.annotations.SerializedName
import org.koin.core.annotation.Single

@Single
data class MotorbikeApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("make") val make: String,
    @SerializedName("model") val model: String,
    @SerializedName("year") val year: String,
    @SerializedName("type") val type: String,
    @SerializedName("displacement") val displacement: String,
    @SerializedName("img") val img: String
)