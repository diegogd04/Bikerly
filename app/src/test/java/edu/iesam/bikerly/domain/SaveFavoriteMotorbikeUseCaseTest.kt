package edu.iesam.bikerly.domain

import androidx.core.net.toUri
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SaveFavoriteMotorbikeUseCaseTest {

    @Test
    fun `When invoke then save favorite motorbike`() = runTest {
        val motorbikeRepositoryMock = mockk<MotorbikeRepository>(relaxed = true)
        val saveFavoriteMotorbikeUseCase = SaveFavoriteMotorbikeUseCase(motorbikeRepositoryMock)
        val motorbike = Motorbike(
            1,
            "Kawasaki",
            "Ninja 650",
            "2022",
            "Sport",
            "649",
            "https://cdn.dealerspike.com/imglib/v1/800x600/imglib/Assets/Inventory/07/7C/077C65E3-5BF7-4435-8BCA-D28995829246.jpg".toUri()
        )

        saveFavoriteMotorbikeUseCase(motorbike)

        coVerify(exactly = 1) { motorbikeRepositoryMock.saveFavoriteMotorbike(motorbike) }
    }
}