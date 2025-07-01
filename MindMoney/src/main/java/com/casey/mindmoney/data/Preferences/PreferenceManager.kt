package com.casey.mindmoney.data.Preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property for accessing DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

object PreferenceKeys {
    val BUDGET_PERIOD = stringPreferencesKey("budget_period")
}

class PreferenceManager(private val context: Context) {

    val budgetPeriodFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.BUDGET_PERIOD] ?: "Weekly"
    }

    suspend fun setBudgetPeriod(value: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.BUDGET_PERIOD] = value
        }
    }
}