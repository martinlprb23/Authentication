package com.roblescode.authentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.roblescode.authentication.presentation.navigation.AppNavHost
import com.roblescode.authentication.presentation.ui.screens.auth.AuthViewModel
import com.roblescode.authentication.presentation.ui.theme.AuthenticationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    if (isSystemInDarkTheme()) {
                        systemUiController.setNavigationBarColor(Color.Black, darkIcons = false)
                        systemUiController.setStatusBarColor(Color.Black, darkIcons = false)
                    }
                    AppNavHost(viewModel)
                }
            }
        }
    }
}
