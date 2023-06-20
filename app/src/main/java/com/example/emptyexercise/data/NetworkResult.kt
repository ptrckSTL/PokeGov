package com.example.emptyexercise.data

sealed class NetworkResult<out ValueT, out ErrT> {

    data class Success<out ValueT>(val value: ValueT) : NetworkResult<ValueT, Nothing>()

    data class Failure<out ErrT>(val error: ErrT) : NetworkResult<Nothing, ErrT>()
}

fun <ValueT, ErrT> NetworkResult<ValueT, ErrT>.fold(
    onSuccess: (ValueT) -> Unit,
    onFailure: (ErrT) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> onSuccess(this.value)
        is NetworkResult.Failure -> onFailure(this.error)
    }
}

class NetworkError(val t: Throwable) // catch-all for our limited app. Could be expanded to some kind of sealed class of different outcomes

/**
 * Wraps a network response into a handy [NetworkResult]
 */
suspend fun <T> networkResultFrom(result: suspend () -> T): NetworkResult<T, NetworkError> = try {
    NetworkResult.Success(result())
} catch (e: Exception) {
    NetworkResult.Failure(NetworkError(e))
}
