package com.example.freegamesapi.ui.personajes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.RickAndMortyRepository
import com.example.freegamesapi.model.PersonajeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PersonajesUiState {
    object Loading : PersonajesUiState
    data class Success(val personajes: List<PersonajeModel>) : PersonajesUiState
    data class Error(val mensaje: String) : PersonajesUiState
}

class PersonajesViewModel(
    private val repository: RickAndMortyRepository = RickAndMortyRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<PersonajesUiState>(PersonajesUiState.Loading)
    val uiState: StateFlow<PersonajesUiState> = _uiState.asStateFlow()

    init {
        cargarPersonajes()
    }

    fun cargarPersonajes() {
        viewModelScope.launch {
            _uiState.value = PersonajesUiState.Loading
            try {
                val lista = repository.obtenerPersonajes()
                _uiState.value = PersonajesUiState.Success(lista)
            } catch (e: Exception) {
                _uiState.value = PersonajesUiState.Error("Error al cargar personajes: ${e.message}")
            }
        }
    }
}
