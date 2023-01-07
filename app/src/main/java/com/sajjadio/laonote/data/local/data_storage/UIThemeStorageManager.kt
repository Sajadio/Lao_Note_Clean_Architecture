package com.sajjadio.laonote.data.local.data_storage

import com.sajjadio.laonote.utils.UiMode
import kotlinx.coroutines.flow.Flow

interface UIThemeStorageManager {
    val uiModeFlow: Flow<String>
    suspend fun setUIMode(uiMode: UiMode)
}