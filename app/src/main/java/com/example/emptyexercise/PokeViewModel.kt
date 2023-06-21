package com.example.emptyexercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emptyexercise.data.PokeRepo
import com.example.emptyexercise.data.fold
import com.example.emptyexercise.ui.models.ViewState
import com.example.emptyexercise.ui.models.toPokeCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: PokeRepo
) : ViewModel() {

    // viewState should be read-only outside of this VM
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    // no need for fancy initialization strategies, if this VM is running it should be loading items
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo
                .getPokemonData()
                .fold(
                    onSuccess = { result ->
                        _viewState.value =
                            ViewState.PokemonFound(result.pokemons.map { it.toPokeCard() })
                    },
                    onFailure = { error ->
                        _viewState.value = ViewState.Error(error.t.localizedMessage)
                    }
                )
        }
    }
}