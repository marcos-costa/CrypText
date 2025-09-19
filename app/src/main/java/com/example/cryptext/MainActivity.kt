package com.example.cryptext

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.UserDataRepository
import com.example.cryptext.ui.theme.CrypTextTheme

private const val USER_DATA_NAME = "user_data"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_DATA_NAME
)

class MainActivity : ComponentActivity() {
    lateinit var userDataRepository: UserDataRepository
    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDataRepository = UserDataRepository(dataStore)
        database = AppDatabase.getDatabase(this)

        setContent {
            CrypTextTheme {
                MyApp(
                    database,
                    userDataRepository
                )
            }
        }
    }
}

