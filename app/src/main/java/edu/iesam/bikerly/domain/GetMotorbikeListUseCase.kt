package edu.iesam.bikerly.domain

import org.koin.core.annotation.Single

@Single
class GetMotorbikeListUseCase(private val repository: MotorbikeRepository) {

    operator fun invoke(): List<Motorbike>{
        return repository.getMotorbikeList()
    }
}