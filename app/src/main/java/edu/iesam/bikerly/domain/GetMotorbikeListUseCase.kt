package edu.iesam.bikerly.domain

class GetMotorbikeListUseCase(private val repository: MotorbikeRepository) {

    operator fun invoke(): List<Motorbike>{
        return repository.getMotorbikeList()
    }
}