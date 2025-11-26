package edu.iesam.bikerly.data.local

import androidx.core.net.toUri
import edu.iesam.bikerly.domain.Motorbike

fun MotorbikeMockModel.toModel(): Motorbike {
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