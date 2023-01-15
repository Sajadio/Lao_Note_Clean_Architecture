package com.sajjadio.laonote.domain.usecase.auth

import com.sajjadio.laonote.domain.usecase.ValidationResult
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
) {

    operator fun invoke(isValidEmail: Boolean): ValidationResult {
        if (!isValidEmail) {
            return ValidationResult(
                successful = false,
                errorMessage = "Email must be valid"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}