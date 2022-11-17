package com.roblescode.authentication.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roblescode.authentication.presentation.navigation.ROUTE_HOME
import com.roblescode.authentication.presentation.navigation.ROUTE_LOADING
import com.roblescode.authentication.presentation.navigation.ROUTE_LOGIN
import com.roblescode.authentication.presentation.ui.screens.auth.AuthViewModel

@Composable
fun LoadingScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val currentUser = viewModel.currentUser
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator(
            modifier = Modifier.width(50.dp),
            color = MaterialTheme.colors.onBackground
        )
    }
    if (currentUser==null){
        navController.navigate(ROUTE_LOGIN){
            popUpTo(ROUTE_LOADING){inclusive = true}
        }
    }else{
        navController.navigate(ROUTE_HOME){
            popUpTo(ROUTE_LOADING){inclusive = true}
        }
    }
}