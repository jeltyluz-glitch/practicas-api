package com.example.freegamesapi.ui.trivia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.OpenTriviaRepository
import com.example.freegamesapi.model.PreguntaTrivia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface TriviaUiState {
    object Loading : TriviaUiState
    data class Success(val preguntas: List<PreguntaTrivia>) : TriviaUiState
    data class Error(val mensaje: String) : TriviaUiState
}

class TriviaViewModel(
    private val repository: OpenTriviaRepository = OpenTriviaRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<TriviaUiState>(TriviaUiState.Loading)
    val uiState: StateFlow<TriviaUiState> = _uiState.asStateFlow()

    init {
        cargarTrivia()
    }

    fun cargarTrivia() {
        viewModelScope.launch {
            _uiState.value = TriviaUiState.Loading
            try {
                val preguntas = repository.obtenerTrivia()
                _uiState.value = TriviaUiState.Success(preguntas)
            } catch (e: Exception) {
                _uiState.value = TriviaUiState.Error("Error al cargar trivia: ${e.message}")
            }
        }
    }
}
