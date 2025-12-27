package edu.iesam.bikerly.data

import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.data.local.mock.MotorbikeMockLocalDataSource
import edu.iesam.bikerly.data.local.room.MotorbikeDbLocalDataSource
import edu.iesam.bikerly.data.remote.MotorbikeFirebaseRemoteDataSource
import edu.iesam.bikerly.data.remote.api.MotorbikeApiRemoteDataSource
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.domain.MotorbikeRepository
import org.koin.core.annotation.Single

@Single
class MotorbikeDataRepository(
    private val mockLocal: MotorbikeMockLocalDataSource,
    private val roomLocal: MotorbikeDbLocalDataSource,
    private val apiRemote: MotorbikeApiRemoteDataSource,
    private val firebaseRemote: MotorbikeFirebaseRemoteDataSource
) : MotorbikeRepository {

    override suspend fun getMotorbikeList(): Result<List<Motorbike>> {
        val localMotorbikes = roomLocal.getMotorbikeList()

        return if (localMotorbikes.isSuccess) {
            localMotorbikes
        } else {
            val remoteMotorbikes = getRemoteMotorbikeList()

            remoteMotorbikes.onSuccess { motorbikeList ->
                roomLocal.saveMotorbikeList(motorbikeList)
            }

            remoteMotorbikes
        }
    }

    suspend fun getRemoteMotorbikeList(): Result<List<Motorbike>> {
        val firebaseRemoteData = firebaseRemote.getMotorbikeList()

        return if (firebaseRemoteData.isSuccess) {
            firebaseRemoteData
        } else {
            val apiRemoteData = apiRemote.getMotorbikeList()

            if (apiRemoteData.isSuccess) {
                val motorbikeList = apiRemoteData.getOrNull()

                if (motorbikeList != null) {
                    firebaseRemote.setMotorbikeList(motorbikeList)
                    apiRemoteData
                } else {
                    Result.failure(ErrorApp.DataError)
                }
            } else {
                Result.failure(ErrorApp.DataError)
            }
        }
    }

    override fun getMotorbikeById(id: Int): Result<Motorbike> {
        return mockLocal.getMotorbikeById(id)
    }
}