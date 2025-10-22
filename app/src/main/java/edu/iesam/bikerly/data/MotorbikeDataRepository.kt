package edu.iesam.bikerly.data

import edu.iesam.bikerly.data.local.MotorbikeMockLocalDataSource
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.domain.MotorbikeRepository

class MotorbikeDataRepository(private val local: MotorbikeMockLocalDataSource) :
    MotorbikeRepository {

    override fun getMotorbikeList(): List<Motorbike> {
        return local.getMotorbikeList()
    }
}