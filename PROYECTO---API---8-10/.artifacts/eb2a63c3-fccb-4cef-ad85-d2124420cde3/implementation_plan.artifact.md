# Implementación de Persistencia con Room para Rick and Morty (Personajes)

## Goal Description
Implement local persistence using Room in the `freeGamesAPI` project for the Rick and Morty characters feature (API + Cache + Favorites), adhering to the requirements of offline-first caching and user favorites management.

## User Review Required
> [!IMPORTANT]
> We will configure Room 2.6.1 with `kapt` and Coroutines Flow support. We will create two tables / entities (or a unified strategy with a `isFavorite` flag or separate tables) for caching API data and user favorites, ensuring offline-first behavior and persistent favorites.

## Proposed Changes

### Build Configuration
#### [MODIFY] [build.gradle.kts (project)](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/build.gradle.kts)
#### [MODIFY] [build.gradle.kts (app)](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/build.gradle.kts)
- Add `kotlin-kapt` plugin.
- Add Room 2.6.1 dependencies (`room-runtime`, `room-ktx`, `room-compiler`).

### Data Layer (Room)
#### [NEW] [PersonajeEntity.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/local/PersonajeEntity.kt)
- Room Entity for storing characters (both cache and favorite status).
#### [NEW] [PersonajeDao.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/local/PersonajeDao.kt)
- DAO for CRUD operations, Flow observation, and favorite toggling.
#### [NEW] [AppDatabase.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/local/AppDatabase.kt)
- Singleton RoomDatabase instance.

### Mappers
#### [MODIFY] [RickAndMortyMapeadores.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/RickAndMortyMapeadores.kt)
- Add mapping functions between `PersonajeEntity` and `PersonajeModel`.

### Repository & ViewModel
#### [MODIFY] [RickAndMortyRepository.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/data/RickAndMortyRepository.kt)
- Implement offline-first logic: fetch from API, save to Room, expose Flow from Room, manage favorites.
#### [MODIFY] [PersonajesViewModel.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/ui/personajes/PersonajesViewModel.kt)
- Observe Flow from repository, handle toggle favorite action.

### UI Layer
#### [MODIFY] [PersonajesView.kt](file:///C:/Users/Victus/Documents/tarea%203%20apis/PROYECTO---API---8-10/app/src/main/java/com/example/freegamesapi/ui/personajes/PersonajesView.kt)
- Add favorite icon button on each character card with visual state (filled/outline heart).

## Verification Plan
### Automated Tests
- Build project successfully with Gradle sync.
### Manual Verification
- Run app on emulator/device, verify characters load, toggle favorites, test offline mode (restart app with network disconnected).
