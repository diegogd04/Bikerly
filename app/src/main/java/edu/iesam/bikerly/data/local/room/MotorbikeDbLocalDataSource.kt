package edu.iesam.bikerly.data.local.room

import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.Motorbike
import org.koin.core.annotation.Single

@Single
class MotorbikeDbLocalDataSource(private val motorbikeDao: MotorbikeDao) {

    fun getMotorbikeList(): Result<List<Motorbike>> {
        val motorbikeList = motorbikeDao.getAll()
            .map { motorbike ->
                motorbike.toModel()
            }
            .sortedByDescending { it.id }

        return if (motorbikeList.isNotEmpty()) {
            Result.success(motorbikeList)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }

    fun saveMotorbikeList(motorbikeList: List<Motorbike>) {
        motorbikeDao.saveAll(*motorbikeList.map { it.toEntity() }.toTypedArray())
    }
}