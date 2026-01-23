package edu.iesam.bikerly.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.GetFavoriteMotorbikeListUseCase
import edu.iesam.bikerly.domain.GetMotorbikeListUseCase
import edu.iesam.bikerly.domain.Motorbike
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MotorbikeListViewModel(
    private val getMotorbikeListUseCase: GetMotorbikeListUseCase,
    private val getFavoriteMotorbikeListUseCase: GetFavoriteMotorbikeListUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    var showFavoriteMotorbikeList = false
    private var favoriteIdList: Set<Int> = emptySet()

    fun loadInitialList() {
        viewModelScope.launch(Dispatchers.IO) {
            updateFavoriteIdList()
            if (showFavoriteMotorbikeList) {
                loadFavoriteMotorbikeList()
            } else {
                loadMotorbikeList()
            }
        }
    }

    fun loadMotorbikeList() {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val motorbikeList = getMotorbikeListUseCase()
            motorbikeList.fold({
                _uiState.postValue(
                    UiState(
                        motorbikeList = it,
                        showFavorites = false,
                        favoriteIdList = favoriteIdList
                    )
                )
            }, { _uiState.postValue(UiState(error = ErrorApp.DataError)) })
        }
    }

    private fun loadFavoriteMotorbikeList() {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteMotorbikeList = getFavoriteMotorbikeListUseCase()
            favoriteMotorbikeList.fold(
                {
                    _uiState.postValue(
                        UiState(
                            motorbikeList = it,
                            showFavorites = true,
                            favoriteIdList = favoriteIdList
                        )
                    )
                },
                { _uiState.postValue(UiState(error = ErrorApp.DataError)) })
        }
    }

    fun toggleFavoriteMotorbikeList() {
        showFavoriteMotorbikeList = !showFavoriteMotorbikeList
        loadInitialList()
    }

    private suspend fun updateFavoriteIdList() {
        favoriteIdList = getFavoriteMotorbikeListUseCase()
            .getOrNull()
            .orEmpty()
            .map { it.id }
            .toSet()
    }


    data class UiState(
        val isLoading: Boolean = false,
        val motorbikeList: List<Motorbike> = emptyList(),
        val showFavorites: Boolean = false,
        val favoriteIdList: Set<Int> = emptySet(),
        val error: ErrorApp? = null
    )
}