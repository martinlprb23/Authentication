package com.roblescode.authentication.presentation.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.roblescode.authentication.presentation.navigation.ROUTE_HOME
import com.roblescode.authentication.presentation.navigation.ROUTE_LOGIN
import com.roblescode.authentication.presentation.ui.screens.auth.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val currentUser = viewModel.currentUser
    val painter = rememberAsyncImagePainter(model = currentUser?.photoUrl)
    val randomImg = rememberAsyncImagePainter(model = "https://picsum.photos/200?random${Math.random()}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter =  if (currentUser?.photoUrl ==null) randomImg else painter,
                contentDescription = "Profile image",
                modifier = Modifier
                    .size(100.dp)
                    .shadow(1.dp, RoundedCornerShape(50))
                    .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Hi, ${currentUser?.displayName ?: "Not Name"}",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Email: ${currentUser?.email ?: "Not Name"}",
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Provider: ${currentUser?.providerId ?: "Not Name"}",
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    viewModel.logout(context)
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_HOME) {
                            inclusive = true
                        }
                    }
                },
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 10.dp, pressedElevation = 15.dp, disabledElevation = 0.dp
                )
            ) {
                Text(text = "Logout")
            }
        }


    }
}