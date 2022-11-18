package com.sajjadio.laonote.utils

sealed class NetworkResponse<out T> {
    object Loading : NetworkResponse<Nothing>()
    data class Success<T>(val data: T?) : NetworkResponse<T>()
    data class Error(val errorMessage: String?) : NetworkResponse<Nothing>()
    fun data() = if (this is Success) data else null
}