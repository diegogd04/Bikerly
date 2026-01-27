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
    private var motorbikeListAll: List<Motorbike> = emptyList()
    private var currentFilter: String = ""
    private val selectedMakes = mutableSetOf<String>()
    private val selectedTypes = mutableSetOf<String>()
    private var minDisplacement: Int? = null
    private var maxDisplacement: Int? = null

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
                motorbikeListAll = it
                applyFilters()
            }, { _uiState.postValue(UiState(error = ErrorApp.DataError)) })
        }
    }

    private fun loadFavoriteMotorbikeList() {
        _uiState.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteMotorbikeList = getFavoriteMotorbikeListUseCase()
            favoriteMotorbikeList.fold(
                {
                    motorbikeListAll = it
                    applyFilters()
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

    fun onSearchFilterChanged(filter: String) {
        currentFilter = filter
        applyFilters()
    }

    fun onMakeFilterChanged(makes: List<String>) {
        selectedMakes.clear()
        selectedMakes.addAll(makes)
        applyFilters()
    }

    fun onTypeFilterChanged(types: List<String>) {
        selectedTypes.clear()
        selectedTypes.addAll(types)
        applyFilters()
    }

    fun onDisplacementFilterChanged(min: Int?, max: Int?) {
        minDisplacement = min
        maxDisplacement = max
        applyFilters()
    }

    private fun applyFilters() {
        val minDisplacement = minDisplacement
        val maxDisplacement = maxDisplacement
        val filteredMotorbikeList = motorbikeListAll.filter { motorbike ->
            val displacement = motorbike.displacement
            val resultSearchFilter = currentFilter.isBlank() || motorbike.model.contains(
                currentFilter,
                ignoreCase = true
            )
            val resultMakeFilter = selectedMakes.isEmpty() || selectedMakes.contains(motorbike.make)
            val resultTypeFilter = selectedTypes.isEmpty() || selectedTypes.contains(motorbike.type)
            val resultMinDisplacementFilter =
                minDisplacement == null || displacement >= minDisplacement
            val resultMaxDisplacementFilter =
                maxDisplacement == null || displacement <= maxDisplacement

            resultSearchFilter && resultMakeFilter && resultTypeFilter && resultMinDisplacementFilter && resultMaxDisplacementFilter
        }

        _uiState.postValue(
            UiState(
                motorbikeList = filteredMotorbikeList,
                showFavorites = showFavoriteMotorbikeList,
                favoriteIdList = favoriteIdList
            )
        )
    }

    fun getSelectedMakes(): List<String> {
        return selectedMakes.toList()
    }

    fun getSelectedTypes(): List<String> {
        return selectedTypes.toList()
    }

    fun getMinDisplacement(): Int? {
        return minDisplacement
    }

    fun getMaxDisplacement(): Int? {
        return maxDisplacement
    }

    data class UiState(
        val isLoading: Boolean = false,
        val motorbikeList: List<Motorbike> = emptyList(),
        val showFavorites: Boolean = false,
        val favoriteIdList: Set<Int> = emptySet(),
        val error: ErrorApp? = null
    )
}