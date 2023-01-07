package com.sajjadio.laonote.data.local.data_storage

import kotlinx.coroutines.flow.Flow
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SessionManagerImpl @Inject constructor(context: Context) : SessionManager {

    private val Context.accessToken by preferencesDataStore(SESSION_MANAGER)
    private val dataStore = context.accessToken

    override val accessToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[JWT_TOKEN_KEY]
        }

    override suspend fun updateSession(token: String?) {
        dataStore.edit { preferences ->
            token?.let {
                preferences[JWT_TOKEN_KEY] = it
            }
        }
    }


    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("JWT_TOKEN_KEY")
    }
}
