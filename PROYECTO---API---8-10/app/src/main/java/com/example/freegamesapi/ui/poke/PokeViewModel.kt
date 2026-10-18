package com.example.freegamesapi.ui.poke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.PokeRepository
import com.example.freegamesapi.model.PokemonModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PokeUiState {
    object Loading : PokeUiState
    data class Success(val pokemones: List<PokemonModel>) : PokeUiState
    data class Error(val mensaje: String) : PokeUiState
}

class PokeViewModel(
    private val repository: PokeRepository = PokeRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<PokeUiState>(PokeUiState.Loading)
    val uiState: StateFlow<PokeUiState> = _uiState.asStateFlow()

    init {
        cargarPokemones()
    }

    fun cargarPokemones() {
        viewModelScope.launch {
            _uiState.value = PokeUiState.Loading
            try {
                val lista = repository.obtenerPokemones()
                _uiState.value = PokeUiState.Success(lista)
            } catch (e: Exception) {
                _uiState.value = PokeUiState.Error("Error al cargar Pokémon: ${e.message}")
            }
        }
    }
}
