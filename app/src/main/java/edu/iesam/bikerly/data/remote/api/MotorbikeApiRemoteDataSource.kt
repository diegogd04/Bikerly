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

    suspend fun getMotorbikeList(): Result<List<Motorbike>> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service: MotorbikeApiService = retrofit.create(MotorbikeApiService::class.java)

        val motorbikeListApiModel = service.getMotorbikeList()

        val motorbikeList = motorbikeListApiModel
            .map { it.toModel() }
            .sortedByDescending { it.id }

        return if (motorbikeList.isNotEmpty()) {
            Result.success(motorbikeList)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }
}