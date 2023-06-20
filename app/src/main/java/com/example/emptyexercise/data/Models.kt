package com.example.emptyexercise.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokeResponse(
    @SerialName("data")
    val pokemons: List<PokemonData>
)

@Serializable
data class PokemonData(
    @SerialName("artist")
    val artist: String,
    @SerialName("id")
    val id: String,
    @SerialName("images")
    val images: Images,
    @SerialName("name")
    val name: String,
    @SerialName("rarity")
    val rarity: String,
)

@Serializable
data class Images(
    @SerialName("large")
    val large: String,
    @SerialName("small")
    val small: String
)

