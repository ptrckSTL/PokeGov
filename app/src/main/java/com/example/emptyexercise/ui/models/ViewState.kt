package com.example.emptyexercise.ui.models

sealed class ViewState {
    object Loading : ViewState()
    data class PokemonFound(val cards: List<PokeCard>) : ViewState()
    data class Error(val message: String?) : ViewState()
}

