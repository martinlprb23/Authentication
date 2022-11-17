package com.roblescode.authentication.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.roblescode.authentication.R
import com.roblescode.authentication.data.Resource
import com.roblescode.authentication.presentation.navigation.ROUTE_HOME
import com.roblescode.authentication.presentation.navigation.ROUTE_LOGIN
import com.roblescode.authentication.presentation.navigation.ROUTE_SIGNUP
import com.roblescode.authentication.presentation.ui.screens.auth.AuthViewModel
import com.roblescode.authentication.presentation.ui.theme.BlueBaseColor
import com.roblescode.authentication.presentation.ui.theme.GrayColor

@Composable
fun RegisterScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val signupFlow = viewModel.signupFlow.collectAsState()
    var isLoading by remember {
        mutableStateOf(false)
    }
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.checklist),
                    contentDescription = "Login image",
                    modifier = Modifier.height(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Register",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                FormRegister(viewModel, navController, isLoading)
                Spacer(modifier = Modifier.height(16.dp))
                signupFlow.value?.let {
                    when (it) {
                        is Resource.Failure -> {
                            Text(
                                text = it.exception.message.toString(),
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                            isLoading = false
                        }
                        is Resource.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                strokeWidth = 1.dp,
                                color = MaterialTheme.colors.onBackground
                            )
                            isLoading = true
                        }
                        is Resource.Success -> {
                            isLoading = false
                            LaunchedEffect(Unit) {
                                navController.navigate(ROUTE_HOME) {
                                    popUpTo(ROUTE_SIGNUP) { inclusive = true }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun FormRegister(viewModel: AuthViewModel, navController: NavHostController, isLoading: Boolean) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = name, onValueChange = { name = it },
            placeholder = { Text(text = "Full name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = GrayColor
            ),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colors.onBackground,
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Name icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email, onValueChange = { email = it.lowercase() },
            placeholder = { Text(text = "Email ID") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = GrayColor
            ),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colors.onBackground,
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = "Email icon"
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password, onValueChange = { password = it },
            placeholder = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = GrayColor
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                Icon(
                    tint = MaterialTheme.colors.onBackground,
                    painter = painterResource(id = R.drawable.password),
                    contentDescription = "Email icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        tint = MaterialTheme.colors.onBackground,
                        painter = painterResource(id = R.drawable.show),
                        contentDescription = "Email icon"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "By signing up, you're agree to our Terms & Conditions and Privacy Policy",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start),
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.signup(name, email, password)
            },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = !isLoading
        ) {
            Text(
                text = "Continue",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Joined us before?",
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Login",
                color = BlueBaseColor,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_SIGNUP) { inclusive = true }
                    }
                })
        }
    }
}
