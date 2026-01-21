package edu.iesam.bikerly.domain

interface MotorbikeRepository {

    suspend fun getMotorbikeList(): Result<List<Motorbike>>
    suspend fun getMotorbikeById(motorbikeId: Int): Result<Motorbike>
    suspend fun saveFavoriteMotorbike(motorbike: Motorbike)
    suspend fun removeFavoriteMotorbike(motorbike: Motorbike)
    suspend fun isFavoriteMotorbike(motorbike: Motorbike): Boolean
    suspend fun getFavoriteMotorbikeList(): Result<List<Motorbike>>
}