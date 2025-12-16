package edu.iesam.bikerly.data.remote.api

import retrofit2.http.GET

interface MotorbikeApiService {

    @GET("motorbikes")
    suspend fun getMotorbikeList(): List<MotorbikeApiModel>
}