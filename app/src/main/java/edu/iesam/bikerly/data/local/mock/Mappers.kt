package edu.iesam.bikerly.data.local.mock

import androidx.core.net.toUri
import edu.iesam.bikerly.domain.Motorbike

fun MotorbikeMockModel.toModel(): Motorbike {
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