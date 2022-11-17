package com.roblescode.authentication.presentation.ui.screens.auth

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.roblescode.authentication.R
import com.roblescode.authentication.data.Resource
import com.roblescode.authentication.presentation.navigation.ROUTE_HOME
import com.roblescode.authentication.presentation.navigation.ROUTE_LOGIN
import com.roblescode.authentication.presentation.navigation.ROUTE_SIGNUP
import com.roblescode.authentication.presentation.ui.theme.BlueBaseColor
import com.roblescode.authentication.presentation.ui.theme.GrayColor


@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavHostController) {
    val loginFlow = viewModel.loginFlow.collectAsState()
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
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = "Login image",
                    modifier = Modifier.height(150.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Login",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                LoginEmail(viewModel, isLoading)
                Spacer(modifier = Modifier.height(16.dp))
                LoginGoogle(viewModel, navController, isLoading)
                Spacer(modifier = Modifier.height(16.dp))
                loginFlow.value?.let {
                    when (it) {
                        is Resource.Failure -> {
                            Text(text = it.exception.message.toString(), color = Color.Red, fontSize = 14.sp)
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
                                    popUpTo(ROUTE_LOGIN) { inclusive = true }
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
fun LoginEmail(viewModel: AuthViewModel, isLoading: Boolean) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = email,
            onValueChange = { email = it.lowercase() },
            label = { Text(text = "Email ID") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                imeAction = ImeAction.Next
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
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
        TextField(value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = GrayColor
            ),
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
            text = "Forget Password?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End),
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = !isLoading,
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp, pressedElevation = 15.dp, disabledElevation = 0.dp
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Login", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White
            )
        }
    }
}


@Composable
fun LoginGoogle(viewModel: AuthViewModel, navController: NavHostController, isLoading: Boolean) {
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(
        viewModel = viewModel
    )
    val token = stringResource(R.string.default_web_client_id)
    Text(text = "OR", fontWeight = FontWeight.Bold, color = Color.LightGray)
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            launcher.launch(googleSignInClient.signInIntent)
        },
        Modifier
            .fillMaxWidth()
            .height(50.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp, pressedElevation = 15.dp, disabledElevation = 0.dp
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = !isLoading,
    ) {
        Icon(
            tint = Color.Unspecified,
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Email icon"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Login with Google",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "New to App?", color = MaterialTheme.colors.onSurface, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Register",
                color = BlueBaseColor,
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    navController.navigate(ROUTE_SIGNUP) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                })
        }
    }

}


@Composable
fun rememberFirebaseAuthLauncher(
    viewModel: AuthViewModel
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        viewModel.loginGoogle(task)
    }
}

