package com.example.freegamesapi.ui.personajes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.RickAndMortyRepository
import com.example.freegamesapi.model.PersonajeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed interface PersonajesUiState {
    object Loading : PersonajesUiState
    data class Success(val personajes: List<PersonajeModel>) : PersonajesUiState
    data class Error(val mensaje: String) : PersonajesUiState
}

class PersonajesViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = RickAndMortyRepository(application)

    private val _uiState = MutableStateFlow<PersonajesUiState>(PersonajesUiState.Loading)
    val uiState: StateFlow<PersonajesUiState> = _uiState.asStateFlow()

    init {
        observarPersonajes()
        sincronizarDatos()
    }

    private fun observarPersonajes() {
        viewModelScope.launch {
            repository.personajesFlow
                .catch { e ->
                    _uiState.value = PersonajesUiState.Error("Error al observar base de datos: ${e.message}")
                }
                .collect { lista ->
                    _uiState.value = PersonajesUiState.Success(lista)
                }
        }
    }

    fun sincronizarDatos() {
        viewModelScope.launch {
            try {
                if (repository.contarLocal() == 0) {
                    _uiState.value = PersonajesUiState.Loading
                }
                repository.refrescarPersonajes()
            } catch (e: Exception) {
                if (repository.contarLocal() == 0) {
                    _uiState.value = PersonajesUiState.Error("Error de red: ${e.message}")
                }
            }
        }
    }

    fun toggleFavorito(personaje: PersonajeModel) {
        viewModelScope.launch {
            repository.toggleFavorito(personaje.id, personaje.isFavorite)
        }
    }
}
