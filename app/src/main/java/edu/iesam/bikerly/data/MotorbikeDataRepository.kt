package edu.iesam.bikerly.data

import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.data.local.favoriteXml.FavoriteMotorbikeXmlLocalDataSource
import edu.iesam.bikerly.data.local.mock.MotorbikeMockLocalDataSource
import edu.iesam.bikerly.data.local.room.MotorbikeDbLocalDataSource
import edu.iesam.bikerly.data.remote.MotorbikeFirebaseRemoteDataSource
import edu.iesam.bikerly.data.remote.api.MotorbikeApiRemoteDataSource
import edu.iesam.bikerly.data.remote.toModel
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.domain.MotorbikeRepository
import org.koin.core.annotation.Single

@Single
class MotorbikeDataRepository(
    private val mockLocal: MotorbikeMockLocalDataSource,
    private val favoriteXmlLocal: FavoriteMotorbikeXmlLocalDataSource,
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

            return remoteMotorbikes.onSuccess { motorbikeList ->
                roomLocal.saveMotorbikeList(motorbikeList)
                Result.success(remoteMotorbikes)
            }
            remoteMotorbikes.onFailure {
                Result.failure<ErrorApp>(ErrorApp.DataError)
            }
        }
    }

    private suspend fun getRemoteMotorbikeList(): Result<List<Motorbike>> {
        val firebaseList = firebaseRemote.getMotorbikeList().getOrNull()
        val apiList = apiRemote.getMotorbikeList().getOrNull()

        return if (firebaseList != null) {
            if (firebaseRemote.isCacheValid(firebaseList)) {
                Result.success(firebaseList.map { it.toModel() })
            } else {
                if (apiList != null) {
                    if (firebaseList.size == apiList.size) {
                        Result.success(firebaseList.map { it.toModel() })
                    } else {
                        firebaseRemote.setMotorbikeList(apiList)
                        Result.success(apiList)
                    }
                } else {
                    Result.failure(ErrorApp.DataError)
                }
            }
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }

    override suspend fun getMotorbikeById(motorbikeId: Int): Result<Motorbike> {
        val localMotorbike = roomLocal.getMotorbikeById(motorbikeId)

        return if (localMotorbike.isSuccess) {
            localMotorbike
        } else {
            val remoteMotorbike = getRemoteMotorbikeById(motorbikeId)

            remoteMotorbike.onSuccess {
                Result.success(remoteMotorbike)
            }
            remoteMotorbike.onFailure {
                Result.failure<ErrorApp>(ErrorApp.DataError)
            }

        }
    }

    private suspend fun getRemoteMotorbikeById(motorbikeId: Int): Result<Motorbike> {
        val firebaseRemoteData = firebaseRemote.getMotorbikeById(motorbikeId)

        return if (firebaseRemoteData.isSuccess) {
            firebaseRemoteData
        } else {
            val apiRemoteData = apiRemote.getMotorbikeById(motorbikeId)

            if (apiRemoteData.isSuccess) {
                apiRemoteData
            } else {
                Result.failure(ErrorApp.DataError)
            }
        }
    }


    override suspend fun saveFavoriteMotorbike(motorbike: Motorbike) {
        favoriteXmlLocal.save(motorbike)
    }

    override suspend fun removeFavoriteMotorbike(motorbike: Motorbike) {
        favoriteXmlLocal.remove(motorbike)
    }

    override suspend fun isFavoriteMotorbike(motorbike: Motorbike): Boolean {
        return favoriteXmlLocal.isFavorite(motorbike)
    }

    override suspend fun getFavoriteMotorbikeList(): Result<List<Motorbike>> {
        return favoriteXmlLocal.getList()
    }
}