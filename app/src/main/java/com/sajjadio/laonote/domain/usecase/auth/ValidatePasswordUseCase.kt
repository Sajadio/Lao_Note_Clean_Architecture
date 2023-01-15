package com.sajjadio.laonote.domain.usecase.auth

import com.sajjadio.laonote.domain.usecase.ValidationResult
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password must be greater than 6 digits"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}