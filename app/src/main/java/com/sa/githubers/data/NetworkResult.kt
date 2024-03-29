package com.sa.githubers.data

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int?, val message: String?) : NetworkResult<Nothing>()

    inline fun <reified R> mapSuccess(transform: (T) -> R): NetworkResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
        }
    }
}

suspend fun <T> networkResult(block: suspend () -> retrofit2.Response<T>): NetworkResult<T> {
    return try {
        val response = block.invoke()
        if (response.isSuccessful && response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else {
            NetworkResult.Error(response.code(), response.errorBody()?.string())
        }
    } catch (e: Exception) {
        NetworkResult.Error(null, e.localizedMessage)
    }
}


