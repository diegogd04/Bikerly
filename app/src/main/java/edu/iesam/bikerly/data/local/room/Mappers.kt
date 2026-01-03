package edu.iesam.bikerly.data.local.room

import androidx.core.net.toUri
import edu.iesam.bikerly.domain.Motorbike

fun MotorbikeEntity.toModel(): Motorbike {
    return Motorbike(
        this.id,
        this.make,
        this.model,
        this.year,
        this.type,
        this.displacement,
        this.img.toUri()
    )
}

fun Motorbike.toEntity(): MotorbikeEntity {
    return MotorbikeEntity(
        this.id,
        this.make,
        this.model,
        this.year,
        this.type,
        this.displacement,
        this.img.toString()
    )
}