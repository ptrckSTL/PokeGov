package com.example.emptyexercise.data

import io.ktor.client.HttpClient
import javax.inject.Inject


class Repo @Inject constructor(private val client: HttpClient) {


}
