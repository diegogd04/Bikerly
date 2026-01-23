package edu.iesam.bikerly.domain

import org.koin.core.annotation.Single

@Single
class GetFavoriteMotorbikeListUseCase(private val repository: MotorbikeRepository) {

    suspend operator fun invoke(): Result<List<Motorbike>> {
        return repository.getFavoriteMotorbikeList()
    }
}