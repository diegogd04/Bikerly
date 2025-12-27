package edu.iesam.bikerly.data.local.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MotorbikeEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "make") val make: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "displacement") val displacement: String,
    @ColumnInfo(name = "img") val img: String
)