package com.example.emptyexercise.ui.models

import com.example.emptyexercise.data.PokemonData

data class PokeCard(
    val name: String,
    val id: String,
    val smallImageUrl: String,
    val largeImageUrl: String,
    val artist: String
)

fun PokemonData.toPokeCard() =
    PokeCard(
        name = name,
        id = id,
        smallImageUrl = images.small,
        largeImageUrl = images.large,
        artist = artist
    )
