package com.example.myjsonparsing


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myjsonparsing.navigation.Navigation
import com.example.myjsonparsing.ui.theme.MyJsonParsingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyJsonParsingTheme {
                Navigation()
            }
        }
    }
}
