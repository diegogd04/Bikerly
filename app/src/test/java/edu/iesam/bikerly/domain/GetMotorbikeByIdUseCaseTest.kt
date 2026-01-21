package edu.iesam.bikerly.domain

import edu.iesam.bikerly.data.MotorbikeDataRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMotorbikeByIdUseCaseTest {

    @Test
    fun `When invoke then return motorbike by id`() = runTest {
        val motorbikeRepositoryMock = mockk<MotorbikeDataRepository>(relaxed = true)
        val getMotorbikeByIdUseCase = GetMotorbikeByIdUseCase(motorbikeRepositoryMock)
        val id = 1234

        getMotorbikeByIdUseCase(id)

        coVerify(exactly = 1) { motorbikeRepositoryMock.getMotorbikeById(id) }
    }

}