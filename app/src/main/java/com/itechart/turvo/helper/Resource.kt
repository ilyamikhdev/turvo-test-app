package com.itechart.turvo.helper


sealed class Resource<out T : Any> {
    data class Loading(val isShow: Boolean = true) : Resource<Nothing>()
    data class Success<out T : Any>(val data: T? = null) : Resource<T>()
    data class Error(val error: ErrorType) : Resource<Nothing>()
}

data class ErrorType(
    val code: Int? = null,
    val error: String? = null
)