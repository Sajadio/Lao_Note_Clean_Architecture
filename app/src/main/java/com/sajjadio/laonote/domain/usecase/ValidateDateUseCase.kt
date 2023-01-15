package com.sajjadio.laonote.domain.usecase

import javax.inject.Inject

class ValidateDateUseCase @Inject constructor() {

    operator fun invoke(date: String): ValidationResult {
        return if (date.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = "You should set date"
            )
        } else
            ValidationResult(
                successful = true
            )
    }
}