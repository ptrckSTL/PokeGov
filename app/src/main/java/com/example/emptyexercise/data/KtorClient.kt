package com.example.emptyexercise.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


val appClient: HttpClient by lazy {
    HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true // lets us coax ints into strings and visa versa
                ignoreUnknownKeys = true // if new parameters are added we won't crash
            })
        }
    }
}