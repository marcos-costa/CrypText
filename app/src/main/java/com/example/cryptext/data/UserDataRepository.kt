package com.example.cryptext.data

import android.graphics.Bitmap
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.math.BigInteger

class UserDataRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object{
        val PRIVATE_KEY = intPreferencesKey("privateKey")
        val NAME = stringPreferencesKey("")
        val USERNAME = stringPreferencesKey("")
        val EMAIL = stringPreferencesKey("")
        val PASSWORD = stringPreferencesKey("")
        val TAG = "UserDataRepository"
    }

    val privateKey: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[PRIVATE_KEY] ?: 0
        }

    val userData = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            mapOf(
                "name" to preferences[NAME],
                "username" to preferences[USERNAME],
                "email" to preferences[EMAIL],
                "password" to preferences[PASSWORD]
            )
        }

    suspend fun savePrivateKey(serverSharedKey: BigInteger) {
        dataStore.edit { preferences ->
            preferences[PRIVATE_KEY] = serverSharedKey.toInt()
        }
    }

    suspend fun saveUserData(name: String, username: String, email: String, password: String) {
        dataStore.edit { preferences ->
            preferences[NAME] = name
            preferences[USERNAME] = username
            preferences[EMAIL] = email
            preferences[PASSWORD] = password
        }
    }
}