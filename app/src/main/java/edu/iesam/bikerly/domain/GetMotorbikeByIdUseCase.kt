package edu.iesam.bikerly.domain

import org.koin.core.annotation.Single

@Single
class GetMotorbikeByIdUseCase(private val repository: MotorbikeRepository) {

    operator fun invoke(id: String): Result<Motorbike> {
        return repository.getMotorbikeById(id)
    }
}