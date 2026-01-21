package edu.iesam.bikerly.domain

import org.koin.core.annotation.Single

@Single
class RemoveFavoriteMotorbikeUseCase(private val repository: MotorbikeRepository) {

    suspend operator fun invoke(motorbike: Motorbike) {
        repository.removeFavoriteMotorbike(motorbike)
    }
}