package edu.iesam.bikerly.domain

import edu.iesam.bikerly.data.MotorbikeDataRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class GetMotorbikeByIdUseCaseTest {

    @Test
    fun `When invoke then return motorbike by id`() {
        val motorbikeRepositoryMock = mockk<MotorbikeDataRepository>(relaxed = true)
        val getMotorbikeByIdUseCase = GetMotorbikeByIdUseCase(motorbikeRepositoryMock)
        val id = "1234"

        getMotorbikeByIdUseCase(id)

        verify(exactly = 1) { motorbikeRepositoryMock.getMotorbikeById(id) }
    }

}