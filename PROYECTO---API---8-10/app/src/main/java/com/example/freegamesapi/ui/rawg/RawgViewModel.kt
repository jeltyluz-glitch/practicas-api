package com.example.freegamesapi.ui.rawg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.RawgRepository
import com.example.freegamesapi.model.JuegoRawg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RawgUiState {
    object Loading : RawgUiState
    data class Success(val juegos: List<JuegoRawg>) : RawgUiState
    data class Error(val mensaje: String) : RawgUiState
}

class RawgViewModel(
    private val repository: RawgRepository = RawgRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<RawgUiState>(RawgUiState.Loading)
    val uiState: StateFlow<RawgUiState> = _uiState.asStateFlow()

    init {
        cargarJuegosRawg()
    }

    fun cargarJuegosRawg(busqueda: String? = null) {
        viewModelScope.launch {
            _uiState.value = RawgUiState.Loading
            try {
                val juegos = repository.obtenerJuegosRawg(busqueda)
                _uiState.value = RawgUiState.Success(juegos)
            } catch (e: Exception) {
                _uiState.value = RawgUiState.Error("Error al cargar catálogo: ${e.message}")
            }
        }
    }
}
