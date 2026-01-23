package edu.iesam.bikerly.domain

import edu.iesam.bikerly.data.MotorbikeDataRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetMotorbikeListUseCaseTest {

    @Test
    fun `When invoke then return motorbike list`() = runTest {
        val motorbikeRepositoryMockk = mockk<MotorbikeDataRepository>(relaxed = true)
        val getMotorbikeListUseCase = GetMotorbikeListUseCase(motorbikeRepositoryMockk)

        getMotorbikeListUseCase()

        coVerify(exactly = 1) { motorbikeRepositoryMockk.getMotorbikeList() }
    }
}