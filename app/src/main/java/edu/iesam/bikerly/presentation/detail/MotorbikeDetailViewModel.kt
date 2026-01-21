package edu.iesam.bikerly.presentation.detail

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.GetMotorbikeByIdUseCase
import edu.iesam.bikerly.domain.IsFavoriteMotorbikeUseCase
import edu.iesam.bikerly.domain.Motorbike
import edu.iesam.bikerly.domain.RemoveFavoriteMotorbikeUseCase
import edu.iesam.bikerly.domain.SaveFavoriteMotorbikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MotorbikeDetailViewModel(
    private val getMotorbikeByIdUseCase: GetMotorbikeByIdUseCase,
    private val saveFavoriteMotorbikeUseCase: SaveFavoriteMotorbikeUseCase,
    private val removeFavoriteMotorbikeUseCase: RemoveFavoriteMotorbikeUseCase,
    private val isFavoriteMotorbikeUseCase: IsFavoriteMotorbikeUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadMotorbikeById(id: Int) {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val motorbike = getMotorbikeByIdUseCase(id)
            motorbike.fold(
                {
                    val favorite = isFavoriteMotorbikeUseCase(it)
                    _uiState.postValue(UiState(motorbike = it, isFavorite = favorite))
                },
                { _uiState.postValue(UiState(error = ErrorApp.DataError)) }
            )
        }
    }

    fun toggleFavoriteMotorbike() {
        _uiState.value?.motorbike?.let { motorbike ->
            viewModelScope.launch(Dispatchers.IO) {
                val favorite = isFavoriteMotorbikeUseCase(motorbike)

                if (favorite) {
                    removeFavoriteMotorbikeUseCase(motorbike)
                } else {
                    saveFavoriteMotorbikeUseCase(motorbike)
                }
                _uiState.postValue(_uiState.value?.copy(isFavorite = !favorite))
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val motorbike: Motorbike = Motorbike(0, "", "", "", "", "", "".toUri()),
        val isFavorite: Boolean = false,
        val error: ErrorApp? = null
    )
}