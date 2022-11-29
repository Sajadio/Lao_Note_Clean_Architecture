package com.sajjadio.laonote.domain.usecase.note

import android.content.Context
import com.sajjadio.laonote.R
import com.sajjadio.laonote.domain.usecase.ValidationResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidateNoteTitleUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(noteTitle: String): ValidationResult {
        if(noteTitle.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.note_title_blank_msg)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}