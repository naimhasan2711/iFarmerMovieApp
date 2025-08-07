package com.nakibul.ifarmermovieapp.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nakibul.ifarmermovieapp.presentation.ui.home.components.MovieLayoutMode
import com.nakibul.ifarmermovieapp.utils.Constant.USER_PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE_NAME)

class UserPreferencesRepository(
    private val context: Context
) {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val LAYOUT_MODE = stringPreferencesKey("layout_mode")
    }

    val isDarkTheme: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DARK_THEME] == true
    }

    val layoutMode: Flow<MovieLayoutMode> = dataStore.data.map { preferences ->
        val layoutModeString = preferences[PreferencesKeys.LAYOUT_MODE] ?: MovieLayoutMode.LIST.name
        try {
            MovieLayoutMode.valueOf(layoutModeString)
        } catch (e: IllegalArgumentException) {
            MovieLayoutMode.LIST // Fallback to LIST mode if there's an issue
        }
    }

    suspend fun setDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDarkTheme
        }
    }

    suspend fun setLayoutMode(layoutMode: MovieLayoutMode) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAYOUT_MODE] = layoutMode.name
        }
    }
}
