package com.sajjadio.laonote.domain.usecase

import android.content.Context
import com.sajjadio.laonote.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidateDateUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(date: String): ValidationResult {
        return if (date.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.date_blank_msg)
            )
        } else
            ValidationResult(
                successful = true
            )
    }
}