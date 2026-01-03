package edu.iesam.bikerly.data.remote.api

import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.data.remote.toModel
import edu.iesam.bikerly.domain.Motorbike
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Single
class MotorbikeApiRemoteDataSource {

    private val baseUrl = "http://bikerly.up.railway.app/"

    private val service: MotorbikeApiService by lazy {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(MotorbikeApiService::class.java)
    }

    suspend fun getMotorbikeList(): Result<List<Motorbike>> {
        val motorbikeList = service.getMotorbikeList()
            .map { it.toModel() }
            .sortedByDescending { it.id }

        return if (motorbikeList.isNotEmpty()) {
            Result.success(motorbikeList)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }

    suspend fun getMotorbikeById(motorbikeId: Int): Result<Motorbike> {
        val motorbike = service.getMotorbikeById(motorbikeId).toModel()

        return if (motorbike != null) {
            Result.success(motorbike)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }
}