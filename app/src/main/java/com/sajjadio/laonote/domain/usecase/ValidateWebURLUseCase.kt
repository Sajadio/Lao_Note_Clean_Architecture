package com.sajjadio.laonote.domain.usecase

import android.content.Context
import android.util.Patterns
import com.sajjadio.laonote.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ValidateWebURLUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    operator fun invoke(url: String): ValidationResult {
        if(!(Patterns.WEB_URL.matcher(url).matches())) {
            return ValidationResult(
                successful = false,
                errorMessage = context.resources.getString(R.string.url_valid)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}