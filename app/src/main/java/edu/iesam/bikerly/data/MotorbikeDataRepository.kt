package edu.iesam.bikerly.data

import edu.iesam.bikerly.data.local.MotorbikeMockLocalDataSource
import edu.iesam.bikerly.data.remote.MotorbikeRemoteDataSource
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.domain.MotorbikeRepository
import org.koin.core.annotation.Single

@Single
class MotorbikeDataRepository(
    private val local: MotorbikeMockLocalDataSource,
    private val remote: MotorbikeRemoteDataSource
) : MotorbikeRepository {

    override suspend fun getMotorbikeList(): Result<List<Motorbike>> {
        return remote.getMotorbikeList()
    }

    override fun getMotorbikeById(id: String): Result<Motorbike> {
        return local.getMotorbikeById(id)
    }
}