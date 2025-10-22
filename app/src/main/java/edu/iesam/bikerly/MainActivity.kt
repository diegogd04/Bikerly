package edu.iesam.bikerly

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.iesam.bikerly.data.MotorbikeDataRepository
import edu.iesam.bikerly.data.local.MotorbikeMockLocalDataSource
import edu.iesam.bikerly.domain.GetMotorbikeListUseCase
import edu.iesam.bikerly.presentation.MotorbikeListViewModel

class MainActivity : AppCompatActivity() {

    val local = MotorbikeMockLocalDataSource()
    val dataRepository = MotorbikeDataRepository(local)
    val getMotorbikeListUseCase = GetMotorbikeListUseCase(dataRepository)
    val viewModel = MotorbikeListViewModel(getMotorbikeListUseCase)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMotorbikeList()
    }

    private fun loadMotorbikeList() {
        viewModel.loadMotorbikeList()
        viewModel.uiState.observe(this) {
            Log.d("@dev", it.toString())
        }
    }
}