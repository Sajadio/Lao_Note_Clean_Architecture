package com.sajjadio.laonote.domain.usecase

import android.content.Context
import com.sajjadio.laonote.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidateTitleUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(title: String): ValidationResult {
        return if (title.isBlank()) {
            ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.title_blank_msg)
            )
        } else
            ValidationResult(
                successful = true
            )
    }
}