package com.example.cryptext.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class UserPreferencesRepository (
    private val dataStore: DataStore<Preferences>
){
    private companion object {
        val NAME = stringPreferencesKey("name")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val SENHA = stringPreferencesKey("senha")
    }


}