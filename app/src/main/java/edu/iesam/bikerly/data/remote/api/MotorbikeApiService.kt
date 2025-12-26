package edu.iesam.bikerly.data.remote.api

import edu.iesam.bikerly.data.remote.MotorbikeRemoteModel
import retrofit2.http.GET

interface MotorbikeApiService {

    @GET("motorbikes")
    suspend fun getMotorbikeList(): List<MotorbikeRemoteModel>
}