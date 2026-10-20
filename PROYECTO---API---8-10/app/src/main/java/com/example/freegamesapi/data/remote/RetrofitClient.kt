package com.example.freegamesapi.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente Retrofit unificado (patrón Singleton con `object`)
 * que centraliza las URLs base y la creación de los servicios API de la aplicación.
 */
object RetrofitClient {

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    private inline fun <reified T> createService(baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

    // 1. FreeGames API (https://www.freetogame.com/api/)
    val freeGamesApi: FreeGamesApiService by lazy {
        createService("https://www.freetogame.com/api/")
    }

    // Alias para compatibilidad con FreeGamesRepository existente
    val api: FreeGamesApiService get() = freeGamesApi

    // 2. CheapShark API (https://www.cheapshark.com/api/1.0/)
    val cheapSharkApi: CheapSharkApiService by lazy {
        createService("https://www.cheapshark.com/api/1.0/")
    }

    // 3. OpenTrivia API (https://opentdb.com/)
    val openTriviaApi: OpenTriviaApiService by lazy {
        createService("https://opentdb.com/")
    }

    // 4. PokeAPI (https://pokeapi.co/api/v2/)
    val pokeApi: PokeApiService by lazy {
        createService("https://pokeapi.co/api/v2/")
    }

    // 5. RAWG API (https://rawg.io/api/)
    val rawgApi: RawgApiService by lazy {
        createService("https://rawg.io/api/")
    }

    // 6. Rick and Morty API (https://rickandmortyapi.com/api/)
    val rickAndMortyApi: RickAndMortyApiService by lazy {
        createService("https://rickandmortyapi.com/api/")
    }
}
