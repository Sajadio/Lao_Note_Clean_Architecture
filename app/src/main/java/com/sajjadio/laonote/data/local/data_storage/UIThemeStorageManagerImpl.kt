package com.sajjadio.laonote.data.local.data_storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sajjadio.laonote.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class UIThemeStorageManagerImpl @Inject constructor(context: Context) : UIThemeStorageManager {

    private val Context.accessToken by preferencesDataStore(UI_THEME_STORAGE)
    private val dataStore = context.accessToken


    override val uiModeFlow: Flow<UiMode?> = dataStore.data
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
                LIGHT -> UiMode.LIGHT
                DARK -> UiMode.DARK
                else -> {
                    null
                }
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
        val SELECTED_THEME = stringPreferencesKey(THEME_APP)
    }
}