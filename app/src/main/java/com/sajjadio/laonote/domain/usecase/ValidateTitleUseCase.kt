package com.sajjadio.laonote.domain.usecase

import javax.inject.Inject

class ValidateTitleUseCase  @Inject constructor(){

    operator fun invoke(title: String): ValidationResult {
        return if (title.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "Title can\'t be empty"
            )
        } else
            ValidationResult(
                successful = true
            )
    }
}