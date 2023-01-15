package com.sajjadio.laonote.domain.usecase

import javax.inject.Inject

class ValidateWebURLUseCase @Inject constructor() {

    operator fun invoke(url: String, isValidEmail: Boolean): ValidationResult {
        if (url.isNotEmpty()) {
            return if (isValidEmail)
                ValidationResult(
                    successful = true
                )
            else
                ValidationResult(
                    successful = false,
                    errorMessage = "Url is not valid"
                )
        }
        return ValidationResult(
            successful = true
        )
    }
}