package com.sajjadio.laonote.domain.usecase.auth

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)