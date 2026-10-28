package com.example.freegamesapi.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

sealed class Resultado<out T> {
    data class Exito<T>(val dato: T) : Resultado<T>()
    data class Error(val mensaje: String) : Resultado<Nothing>()
}

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun usuarioActual(): FirebaseUser? = auth.currentUser

    fun observarSesion(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    suspend fun iniciarSesion(email: String, contrasena: String): Resultado<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, contrasena).await()
            Resultado.Exito(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Resultado.Error(traducirError(e))
        }
    }

    suspend fun registrar(email: String, contrasena: String): Resultado<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, contrasena).await()
            Resultado.Exito(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Resultado.Error(traducirError(e))
        }
    }

    suspend fun enviarCorreoDeRecuperacion(email: String): Resultado<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Resultado.Exito(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Resultado.Error(traducirError(e))
        }
    }

    suspend fun cerrarSesion() {
        auth.signOut()
    }

    private fun traducirError(e: Exception): String {
        val mensaje = e.message ?: ""
        return when {
            mensaje.contains("password", ignoreCase = true) -> "La contraseña debe tener al menos 6 caracteres o es demasiado débil."
            mensaje.contains("email", ignoreCase = true) || mensaje.contains("address is badly formatted", ignoreCase = true) -> "El formato del correo electrónico no es válido."
            mensaje.contains("already in use", ignoreCase = true) -> "Este correo electrónico ya está registrado."
            mensaje.contains("invalid credential", ignoreCase = true) || mensaje.contains("user not found", ignoreCase = true) || mensaje.contains("wrong password", ignoreCase = true) -> "Correo o contraseña incorrectos."
            mensaje.contains("network", ignoreCase = true) -> "Problema de conexión. Verifica tu red."
            else -> "Ocurrió un error inesperado: ${e.localizedMessage}"
        }
    }
}
