package edu.iesam.bikerly.data.remote.api

import edu.iesam.bikerly.data.remote.MotorbikeRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path

interface MotorbikeApiService {

    @GET("motorbikes")
    suspend fun getMotorbikeList(): List<MotorbikeRemoteModel>

    @GET("motorbikes/{id}")
    suspend fun getMotorbikeById(@Path("id") id: Int): MotorbikeRemoteModel
}