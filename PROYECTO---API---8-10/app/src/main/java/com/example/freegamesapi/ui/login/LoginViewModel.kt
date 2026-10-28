package com.example.freegamesapi.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.freegamesapi.data.AuthRepository
import com.example.freegamesapi.data.Resultado
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    data class Success(val message: String) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isRegisterMode by mutableStateOf(false)
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    val currentUser: StateFlow<FirebaseUser?> = authRepository.observarSesion()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = authRepository.usuarioActual()
        )

    fun onEmailChange(newEmail: String) {
        email = newEmail
        uiState = AuthUiState.Idle
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        uiState = AuthUiState.Idle
    }

    fun toggleAuthMode() {
        isRegisterMode = !isRegisterMode
        uiState = AuthUiState.Idle
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun submit() {
        if (email.isBlank() || password.isBlank()) {
            uiState = AuthUiState.Error("Por favor, completa todos los campos.")
            return
        }
        if (password.length < 6) {
            uiState = AuthUiState.Error("La contraseña debe tener al menos 6 caracteres.")
            return
        }

        uiState = AuthUiState.Loading
        viewModelScope.launch {
            val resultado = if (isRegisterMode) {
                authRepository.registrar(email.trim(), password)
            } else {
                authRepository.iniciarSesion(email.trim(), password)
            }

            uiState = when (resultado) {
                is Resultado.Exito -> AuthUiState.Success(if (isRegisterMode) "Registro exitoso" else "Inicio de sesión exitoso")
                is Resultado.Error -> AuthUiState.Error(resultado.mensaje)
            }
        }
    }

    fun recuperarContrasena() {
        if (email.isBlank()) {
            uiState = AuthUiState.Error("Ingresa tu correo electrónico para recuperar la contraseña.")
            return
        }
        uiState = AuthUiState.Loading
        viewModelScope.launch {
            val resultado = authRepository.enviarCorreoDeRecuperacion(email.trim())
            uiState = when (resultado) {
                is Resultado.Exito -> AuthUiState.Success("Correo de recuperación enviado. Revisa tu bandeja.")
                is Resultado.Error -> AuthUiState.Error(resultado.mensaje)
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            authRepository.cerrarSesion()
        }
    }
}
