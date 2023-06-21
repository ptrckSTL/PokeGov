package com.example.emptyexercise.ui.models

import androidx.compose.runtime.Stable
import com.example.emptyexercise.data.PokemonData

@Stable
data class PokeCard(
    val name: String,
    val id: String,
    val smallImageUrl: String,
    val largeImageUrl: String,
    val artist: String
) {
    companion object {
        // helpful for mocking or when you need a placeholder
        val empty = PokeCard("", "", "", "", "")
    }
}

fun PokemonData.toPokeCard() =
    PokeCard(
        name = name,
        id = id,
        smallImageUrl = images.small,
        largeImageUrl = images.large,
        artist = artist
    )
