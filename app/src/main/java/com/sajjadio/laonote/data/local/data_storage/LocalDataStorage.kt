package com.sajjadio.laonote.data.local.data_storage

import com.sajjadio.laonote.utils.UiMode
import kotlinx.coroutines.flow.Flow


interface LocalDataStorage {
    val accessToken: Flow<String?>
    suspend fun updateSession(token: String?)
    suspend fun logout()

    val uiModeFlow: Flow<String>
    suspend fun setUIMode(uiMode: UiMode)
}