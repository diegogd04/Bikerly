package edu.iesam.bikerly.data.remote

import com.google.gson.annotations.SerializedName
import org.koin.core.annotation.Single

@Single
data class MotorbikeRemoteModel(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("make") val make: String = "",
    @SerializedName("model") val model: String = "",
    @SerializedName("year") val year: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("displacement") val displacement: String = "",
    @SerializedName("img") val img: String = ""
)