package com.example.emptyexercise.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject


interface PokeRepo {
    suspend fun getPokemonData(): NetworkResult<PokeResponse, NetworkError>


    class Impl @Inject constructor(private val client: HttpClient) : PokeRepo {
        override suspend fun getPokemonData() = networkResultFrom {
            client.get("https://api.pokemontcg.io/v2/cards?q=set.name:base").body<PokeResponse>()
        }
    }

}
