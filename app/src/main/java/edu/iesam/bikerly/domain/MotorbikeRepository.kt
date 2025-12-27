package edu.iesam.bikerly.domain

interface MotorbikeRepository {

    suspend fun getMotorbikeList(): Result<List<Motorbike>>
    suspend fun getMotorbikeById(motorbikeId: Int): Result<Motorbike>
}