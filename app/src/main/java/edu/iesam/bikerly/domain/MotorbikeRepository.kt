package edu.iesam.bikerly.domain

interface MotorbikeRepository {

    fun getMotorbikeList(): List<Motorbike>
}