package com.sajjadio.laonote.data.local.data_storage

import com.sajjadio.laonote.utils.UiMode
import kotlinx.coroutines.flow.Flow


interface SessionManager {
    val accessToken: Flow<String?>
    suspend fun updateSession(token: String?)
    suspend fun logout()
}