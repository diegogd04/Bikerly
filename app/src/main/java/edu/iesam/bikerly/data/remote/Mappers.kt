package edu.iesam.bikerly.data.remote

import androidx.core.net.toUri
import edu.iesam.bikerly.domain.Motorbike

fun MotorbikeRemoteModel.toModel(): Motorbike {
    return Motorbike(
        this.id,
        this.make,
        this.model,
        this.year.toInt(),
        this.type,
        this.displacement.toInt(),
        this.img.toUri()
    )
}

fun Motorbike.toRemoteModel(ms: Long): MotorbikeRemoteModel {
    return MotorbikeRemoteModel(
        this.id,
        this.make,
        this.model,
        this.year.toString(),
        this.type,
        this.displacement.toString(),
        this.img.toString(),
        ms
    )
}