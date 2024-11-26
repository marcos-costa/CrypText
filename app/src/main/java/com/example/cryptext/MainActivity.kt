package com.example.cryptext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.cryptext.data.AppDatabase
import com.example.cryptext.data.fakeData
import com.example.cryptext.ui.theme.CrypTextTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)

        lifecycleScope.launch(Dispatchers.IO) {
            fakeData(database)
        }

        setContent {
            CrypTextTheme {
                MyApp(database)
            }
        }
    }
}

