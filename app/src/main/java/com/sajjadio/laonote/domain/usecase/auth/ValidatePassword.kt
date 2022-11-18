package com.sajjadio.laonote.domain.usecase.auth

import android.content.Context
import com.sajjadio.laonote.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidatePassword @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.new_password_error_msg)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}