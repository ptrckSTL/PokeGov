package com.example.emptyexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.emptyexercise.data.PokemonData
import com.example.emptyexercise.ui.theme.PokeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: PokeViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            PokeTheme {
                Column {
                    val pokemonData by remember { mutableStateOf<List<PokemonData>>(emptyList()) }
                    val isError by remember { mutableStateOf("") }
                    Text("error = $isError")
                    pokemonData.forEach {
                        Text(it.name)
                    }
                }
            }
        }
    }

}

