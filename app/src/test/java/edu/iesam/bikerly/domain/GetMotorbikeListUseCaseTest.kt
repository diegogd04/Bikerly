package edu.iesam.bikerly.domain

import edu.iesam.bikerly.data.MotorbikeDataRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class GetMotorbikeListUseCaseTest {

    @Test
    fun `When invoke then return motorbike list`() {
        val motorbikeRepositoryMockk = mockk<MotorbikeDataRepository>(relaxed = true)
        val getMotorbikeListUseCase = GetMotorbikeListUseCase(motorbikeRepositoryMockk)

        getMotorbikeListUseCase()

        verify(exactly = 1) { motorbikeRepositoryMockk.getMotorbikeList() }
    }
}