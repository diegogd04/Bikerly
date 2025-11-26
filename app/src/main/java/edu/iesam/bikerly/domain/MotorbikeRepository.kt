package edu.iesam.bikerly.domain

interface MotorbikeRepository {

    fun getMotorbikeList(): Result<List<Motorbike>>
}