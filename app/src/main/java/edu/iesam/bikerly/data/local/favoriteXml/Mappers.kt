package edu.iesam.bikerly.data.local.favoriteXml

import androidx.core.net.toUri
import edu.iesam.bikerly.domain.Motorbike

fun Motorbike.toXmlModel(): MotorbikeXmlModel {
    return MotorbikeXmlModel(
        this.id,
        this.make,
        this.model,
        this.year,
        this.type,
        this.displacement.toString(),
        this.img.toString()
    )
}

fun MotorbikeXmlModel.toModel(): Motorbike {
    return Motorbike(
        this.id,
        this.make,
        this.model,
        this.year,
        this.type,
        this.displacement.toInt(),
        this.img.toUri()
    )
}