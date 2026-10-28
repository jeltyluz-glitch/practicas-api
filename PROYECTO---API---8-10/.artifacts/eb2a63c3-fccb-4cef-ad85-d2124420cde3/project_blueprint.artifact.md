# Master Blueprint / Prompt: Arquitectura Base para Apps Android con Múltiples APIs, MVVM y Persistencia de Favoritos (Fase 1)

Utiliza este documento como plantilla o prompt maestro para estructurar un nuevo proyecto Android en Jetpack Compose que consuma múltiples APIs REST y gestione persistencia local offline (Favoritos).

---

## 1. Stack Tecnológico y Librerías Requeridas (`libs.versions.toml` / `build.gradle.kts`)

* **Lenguaje**: Kotlin 2.x
* **UI**: Jetpack Compose (Material 3)
* **Arquitectura**: MVVM (Model - View - ViewModel) + Repositorio unificado
* **Red (API)**:
  * `Retrofit` (v2.x) + `Gson Converter`
  * `OkHttp` (Logging Interceptor)
* **Carga de Imágenes**: `Coil` (`coil-compose`)
* **Persistencia Local**: Base de datos local (SQLite / Room) con soporte reactivo (`Flow` / `SharedFlow`)

---

## 2. Estructura de Paquetes y Clases Obligatorias

Organiza tu paquete principal (`com.example.tuproyecto`) de la siguiente manera:

```text
com.example.tuproyecto/
│
├── data/
│   ├── local/
│   │   └── PersonajeDbHelper.kt (o AppDatabase / DAO para persistencia local)
│   ├── remote/
│   │   ├── RetrofitClient.kt (Cliente unificado con todas las URL base)
│   │   ├── TuApiServices.kt (Interfaces @GET de Retrofit)
│   │   └── TuApiDtos.kt (Data Transfer Objects para el JSON de la API)
│   ├── Mapeadores.kt (Conversión de DTO -> Modelo de Dominio)
│   └── TuRepositorio.kt (Coordina API y Base de Datos Local)
│
├── model/
│   └── TuModelo.kt (Clase de dominio utilizada por UI y ViewModels)
│
├── ui/
│   ├── tu_feature/
│   │   ├── TuView.kt (Pantalla en Jetpack Compose)
│   │   └── TuViewModel.kt (AndroidViewModel exponiendo StateFlow y operaciones)
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── MainActivity.kt
```

---

## 3. Patrones y Componentes Clave

### A. Cliente Retrofit Unificado (`RetrofitClient.kt`)
Centraliza todas las conexiones HTTP y URLs base en un único objeto Singleton:
```kotlin
object RetrofitClient {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private inline fun <reified T> createService(baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

    val apiRickAndMorty: RickAndMortyApiService by lazy { createService("https://rickandmortyapi.com/api/") }
    val apiFreeGames: FreeGamesApiService by lazy { createService("https://www.freetogame.com/api/") }
    // Otras APIs...
}
```

### B. Repositorio con Estrategia Offline-First y Favoritos (`Repositorio`)
* La base de datos local actúa como **fuente única de verdad** para la interfaz.
* Expone los datos mediante `Flow`.
* Sincroniza con la API en segundo plano sin sobrescribir el estado de favoritos del usuario (`isFavorite`).

### C. ViewModel Reactivo (`AndroidViewModel`)
* Extiende de `AndroidViewModel` para tener acceso al `Application Context` e inicializar el repositorio local.
* Expone un `StateFlow<UiState>` (`Loading`, `Success`, `Error`).
* Gestiona acciones asíncronas con `viewModelScope.launch` y `Dispatchers.IO`.

---

## 4. Prompt para Nuevos Proyectos (Copia y Pega)

> **Copia el siguiente texto cuando inicies un nuevo proyecto para aplicar exactamente esta misma arquitectura:**
>
> *"Actúa como un Arquitecto de Software Senior en Android. Necesito que desarrolles la estructura base de mi aplicación en Kotlin y Jetpack Compose utilizando el patrón **MVVM**, un **Repositorio centralizado**, **Retrofit** para consumo de múltiples APIs REST con un `RetrofitClient` unificado, **Coil** para imágenes, y persistencia local (SQLite/Room) enfocada en **Fase 1 (Caché local y gestión persistente de Favoritos)**.*
>
> *Sigue estrictamente la separación de responsabilidades:*
> 1. *DTOs separados de los Modelos de Dominio mediante Mapeadores explícitos.*
> 2. *Repositorio que exponga datos mediante `Flow` para actualización reactiva en la UI.*
> 3. *ViewModels basados en `AndroidViewModel` con `StateFlow`.*
> 4. *Tarjetas en Compose con botones interactivos para alternar favoritos.*"
