package edu.iesam.bikerly.domain

import org.koin.core.annotation.Single

@Single
class IsFavoriteMotorbikeUseCase(private val repository: MotorbikeRepository) {

    suspend operator fun invoke(motorbike: Motorbike): Boolean {
        return repository.isFavoriteMotorbike(motorbike)
    }
}