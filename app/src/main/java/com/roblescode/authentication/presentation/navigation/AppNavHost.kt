package com.roblescode.authentication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.roblescode.authentication.presentation.ui.components.LoadingScreen
import com.roblescode.authentication.presentation.ui.screens.RegisterScreen
import com.roblescode.authentication.presentation.ui.screens.auth.AuthViewModel
import com.roblescode.authentication.presentation.ui.screens.auth.LoginScreen
import com.roblescode.authentication.presentation.ui.screens.home.HomeScreen

@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOADING
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable(ROUTE_LOGIN){
            LoginScreen( viewModel ,navController)
        }
        composable(ROUTE_SIGNUP){
            RegisterScreen(viewModel ,navController)
        }
        composable(ROUTE_HOME){
            HomeScreen(viewModel,navController)
        }
        composable(ROUTE_LOADING){
            LoadingScreen(viewModel = viewModel, navController = navController)
        }
    }
}