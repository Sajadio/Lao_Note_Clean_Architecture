package com.sajjadio.laonote.domain.usecase.auth

import android.content.Context
import android.util.Patterns
import com.sajjadio.laonote.R
import com.sajjadio.laonote.domain.usecase.ValidationResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.email_blank_msg)
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.email_valied_msg)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}