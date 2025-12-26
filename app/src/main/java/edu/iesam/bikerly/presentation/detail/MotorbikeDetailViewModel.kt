package edu.iesam.bikerly.presentation.detail

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.GetMotorbikeByIdUseCase
import edu.iesam.bikerly.domain.Motorbike
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MotorbikeDetailViewModel(
    private val getMotorbikeByIdUseCase: GetMotorbikeByIdUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadMotorbikeById(id: Int) {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val motorbike = getMotorbikeByIdUseCase(id)
            motorbike.fold(
                { _uiState.postValue(UiState(motorbike = it)) },
                { _uiState.postValue(UiState(error = ErrorApp.DataError)) }
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val motorbike: Motorbike = Motorbike(0, "", "", "", "", "", "".toUri()),
        val error: ErrorApp? = null
    )
}