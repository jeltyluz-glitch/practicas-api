# Migración a SQLite Cloud WebLite REST API (Sin Backend Local ni SQLite en dispositivo)

## Goal Description

Eliminar el servidor intermediario de Node.js y la base de datos local SQLite (`PersonajeDbHelper`), migrando la aplicación `freeGamesAPI` para que se conecte **directamente a SQLite Cloud mediante su API REST WebLite (`/v2/weblite/sql`)** utilizando Retrofit y OkHttp con autenticación Bearer.

## User Review Required

> [!IMPORTANT]
> - Se actualizará `RetrofitClient` para incluir el cliente de SQLite Cloud con cabecera Bearer automática.
> - Se reescribirá `CloudApiService` para usar el endpoint universal `/v2/weblite/sql`.
> - Se actualizará `RickAndMortyRepository` para realizar consultas SQL directas (`SELECT`, `INSERT`, `UPDATE`) a la nube.
> - Se eliminará `PersonajeDbHelper.kt` para transicionar completamente a una arquitectura en la nube.

## Proposed Changes

### Remote & Network Layer
#### [MODIFY] [RetrofitClient.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/remote/RetrofitClient.kt)
- Añadir el cliente OkHttp con interceptor para inyectar `Authorization: Bearer <API_KEY>` y base URL `https://caqqbdkqdk.g4.sqlite.cloud/`.
#### [MODIFY] [CloudApiService.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/remote/CloudApiService.kt)
- Definir `SqlRequest`, `SqlResponse` y el método `ejecutarSql`.

### Repository Layer
#### [MODIFY] [RickAndMortyRepository.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/RickAndMortyRepository.kt)
- Reemplazar `PersonajeDbHelper` por consultas directas a `cloudApi` usando SQL (WebLite).
- Si la nube está vacía al iniciar, poblarla con los datos de la API pública.

### Local Cleanup
#### [DELETE] [PersonajeDbHelper.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/local/PersonajeDbHelper.kt)
- Eliminar la base de datos local ya que ahora todo se almacena en SQLite Cloud.

## Verification Plan

### Automated Tests
- Compilar el proyecto con `gradle_build` (`app:assembleDebug`) para validar que no existan errores de tipos o referencias rotas.
### Manual Verification
- Ejecutar la app en el emulador, verificar que los personajes carguen desde la nube, alternar favoritos y comprobar que se guarden en SQLite Cloud.
