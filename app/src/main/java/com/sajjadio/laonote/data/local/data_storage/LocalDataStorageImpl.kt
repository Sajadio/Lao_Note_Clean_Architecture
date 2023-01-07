package com.sajjadio.laonote.data.local.data_storage

import kotlinx.coroutines.flow.Flow
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.accessToken by preferencesDataStore(STORAGE_NAME)

class LocalDataStorageImpl @Inject constructor(context: Context) : LocalDataStorage {

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

    override val uiModeFlow: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            when (preference[SELECTED_THEME]) {
                LIGHT -> LIGHT
                DARK -> DARK
                else -> ""
            }
        }

    // update value in storage
    override suspend fun setUIMode(uiMode: UiMode) {
        dataStore.edit { preferences ->
            preferences[SELECTED_THEME] = when (uiMode) {
                UiMode.LIGHT -> LIGHT
                UiMode.DARK -> DARK
            }.toString()
        }
    }

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("JWT_TOKEN_KEY")
        val SELECTED_THEME = stringPreferencesKey(THEME_APP)

    }
}
