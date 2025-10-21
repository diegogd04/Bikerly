package edu.iesam.bikerly.data

import edu.iesam.bikerly.data.local.MotorbikeMockLocalDataSource
import edu.iesam.bikerly.domain.Motorbike

class MotorbikeDataRepository(private val local: MotorbikeMockLocalDataSource) {

    fun getMotorbikeList(): List<Motorbike> {
        return local.getMotorbikeList()
    }
}