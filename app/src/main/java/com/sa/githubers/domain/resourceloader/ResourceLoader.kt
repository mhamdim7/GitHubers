package com.sa.githubers.domain.resourceloader

import com.sa.githubers.data.NetworkResult
import kotlinx.coroutines.flow.flow

suspend inline fun <reified T> resourceFlow(
    crossinline block: suspend () -> NetworkResult<T>
) = flow<ResourceState<T>> {
    emit(ResourceState.Loading())
    runCatching { block.invoke() }.onSuccess { result ->
        when (result) {
            is NetworkResult.Success -> emit(ResourceState.Success(result.data))
            is NetworkResult.Error -> emit(ResourceState.Error(result.message ?: "Unknown error"))
        }
    }.onFailure { exception ->
        emit(ResourceState.Error(exception.localizedMessage ?: "Unknown error"))
    }
}
