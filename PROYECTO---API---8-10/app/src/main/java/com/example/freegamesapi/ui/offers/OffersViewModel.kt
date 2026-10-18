package com.example.freegamesapi.ui.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.CheapSharkRepository
import com.example.freegamesapi.model.Oferta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface OffersUiState {
    object Loading : OffersUiState
    data class Success(val ofertas: List<Oferta>) : OffersUiState
    data class Error(val mensaje: String) : OffersUiState
}

class OffersViewModel(
    private val repository: CheapSharkRepository = CheapSharkRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<OffersUiState>(OffersUiState.Loading)
    val uiState: StateFlow<OffersUiState> = _uiState.asStateFlow()

    init {
        cargarOfertas()
    }

    fun cargarOfertas() {
        viewModelScope.launch {
            _uiState.value = OffersUiState.Loading
            try {
                val ofertas = repository.obtenerOfertas()
                _uiState.value = OffersUiState.Success(ofertas)
            } catch (e: Exception) {
                _uiState.value = OffersUiState.Error("Error al cargar ofertas: ${e.message}")
            }
        }
    }
}
