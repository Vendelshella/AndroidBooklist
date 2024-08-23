package com.example.androidbooklist


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidbooklist.navigation.Navigation
import com.example.androidbooklist.ui.theme.AndroidBooklistTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBooklistTheme {
                Navigation()
            }
        }
    }
}
