package edu.iesam.bikerly.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.GetMotorbikeListUseCase
import edu.iesam.bikerly.domain.Motorbike
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MotorbikeListViewModel(
    private val getMotorbikeListUseCase: GetMotorbikeListUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadMotorbikeList() {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val motorbikeList = getMotorbikeListUseCase()
            motorbikeList.fold(
                { _uiState.postValue(UiState(motorbikeList = it)) },
                { _uiState.postValue(UiState(error = ErrorApp.DataError)) }
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        var motorbikeList: List<Motorbike> = emptyList(),
        val error: ErrorApp? = null
    )
}