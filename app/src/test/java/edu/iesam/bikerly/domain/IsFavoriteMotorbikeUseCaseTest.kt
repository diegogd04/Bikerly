package edu.iesam.bikerly.domain

import androidx.core.net.toUri
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IsFavoriteMotorbikeUseCaseTest {

    @Test
    fun `When invoke and motorbike is favorite then returns true`() = runTest {
        val motorbikeRepositoryMock = mockk<MotorbikeRepository>()
        val isFavoriteMotorbikeUseCase = IsFavoriteMotorbikeUseCase(motorbikeRepositoryMock)
        val motorbike = Motorbike(
            1,
            "Kawasaki",
            "Ninja 650",
            "2022",
            "Sport",
            "649",
            "https://cdn.dealerspike.com/imglib/v1/800x600/imglib/Assets/Inventory/07/7C/077C65E3-5BF7-4435-8BCA-D28995829246.jpg".toUri()
        )

        coEvery { motorbikeRepositoryMock.isFavoriteMotorbike(motorbike) } returns true

        val result = isFavoriteMotorbikeUseCase(motorbike)
        assertTrue(result)
    }

    @Test
    fun `When invoke and motorbike is not favorite then returns false`() = runTest {
        val repositoryMock = mockk<MotorbikeRepository>()
        val useCase = IsFavoriteMotorbikeUseCase(repositoryMock)

        val motorbike = Motorbike(
            2,
            "Kawasaki",
            "KX250",
            "2022",
            "Cross / motocross",
            "250",
            "https://storage.kawasaki.eu/public/kawasaki.eu/en-EU/model/imported/BE00000340EBFBEAA5.jpg".toUri()
        )

        coEvery { repositoryMock.isFavoriteMotorbike(motorbike) } returns false

        val result = useCase(motorbike)
        assertFalse(result)
    }
}