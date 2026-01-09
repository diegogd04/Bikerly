package edu.iesam.bikerly.data.local.room

import edu.iesam.bikerly.app.di.LOCAL_TIME_CACHE
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.Motorbike
import org.koin.core.annotation.Single

@Single
class MotorbikeDbLocalDataSource(private val motorbikeDao: MotorbikeDao) {

    fun getMotorbikeList(): Result<List<Motorbike>> {
        val motorbikeEntityList = motorbikeDao.getAll()

        return if (motorbikeEntityList.isNotEmpty()) {
            if (motorbikeEntityList[0].createdAt.plus(LOCAL_TIME_CACHE) > System.currentTimeMillis()) {
                val motorbikeList = motorbikeEntityList.map { motorbike ->
                    motorbike.toModel()
                }.sortedByDescending { motorbike ->
                    motorbike.id
                }
                Result.success(motorbikeList)
            } else {
                Result.failure(ErrorApp.DataError)
            }
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }

    fun saveMotorbikeList(motorbikeList: List<Motorbike>) {
        val ms = System.currentTimeMillis()

        motorbikeDao.saveAll(*motorbikeList.map { it.toEntity(ms) }.toTypedArray())
    }

    fun getMotorbikeById(motorbikeId: Int): Result<Motorbike> {
        val motorbike = motorbikeDao.getById(motorbikeId).toModel()

        return if (motorbike != null) {
            Result.success(motorbike)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }
}