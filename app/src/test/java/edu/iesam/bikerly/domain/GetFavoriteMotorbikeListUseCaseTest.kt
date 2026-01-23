package edu.iesam.bikerly.domain

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetFavoriteMotorbikeListUseCaseTest {

    @Test
    fun `When invoke then return favorite motorbike list`() = runTest {
        val motorbikeRepositoryMock = mockk<MotorbikeRepository>(relaxed = true)
        val getFavoriteMotorbikeListUseCase =
            GetFavoriteMotorbikeListUseCase(motorbikeRepositoryMock)

        getFavoriteMotorbikeListUseCase()

        coVerify(exactly = 1) { motorbikeRepositoryMock.getFavoriteMotorbikeList() }
    }
}