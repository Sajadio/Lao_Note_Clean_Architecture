package com.sajjadio.laonote.domain.usecase

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)