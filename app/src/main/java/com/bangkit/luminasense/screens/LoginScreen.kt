package com.bangkit.luminasense.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bangkit.luminasense.backend.preferences.SharedPrefHelper
import com.bangkit.luminasense.backend.service.ApiConfig
import com.bangkit.luminasense.backend.service.LoginRequest
import com.bangkit.luminasense.backend.service.LoginResponse
import com.bangkit.luminasense.components.CustomButton
import com.bangkit.luminasense.components.CustomTextFormField
import com.bangkit.luminasense.components.LoadingAnimation
import com.bangkit.luminasense.components.ValidationType
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kAccentColor
import com.bangkit.luminasense.ui.theme.kDarkColor
import com.bangkit.luminasense.ui.theme.kLightColor
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var (email, setEmail) = remember {
        mutableStateOf("")
    }
    val (pass, setPass) = rememberSaveable {
        mutableStateOf("")
    }

    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = kDarkColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "LOGIN",
                            style = TextStyles.boldTextStyle.copy(
                                fontSize = 20.sp,
                                color = kLightColor,
                            )
                        )
                        Spacer(modifier = Modifier.height(48.dp))

                        CustomTextFormField(
                            title = "Email*",
                            hintText = "Masukkan email anda",
                            value = email,
                            onValueChange = setEmail,
                            validationType = ValidationType.Email,
                            isPassword = false
                        )

                        CustomTextFormField(
                            title = "Password*",
                            hintText = "Masukkan password anda",
                            value = pass,
                            onValueChange = setPass,
                            validationType = ValidationType.Password,
                            isPassword = true
                        )

                        Spacer(modifier = Modifier.height(48.dp))
                        Box(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            CustomButton(
                                title = "Login",
                                color = kAccentColor,
                                titleColor = kLightColor,
                                onTap = {
                                    if (!isLoading) {
                                        isLoading = true
                                        println("Email: $email, Password: $pass")

                                        coroutineScope.launch {
                                            val apiService = ApiConfig.getApiService()
                                            val loginRequest = LoginRequest(email = email, password = pass)
                                            apiService.login(loginRequest).enqueue(object :
                                                Callback<LoginResponse> {
                                                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                                    if (response.isSuccessful) {
                                                        val token = response.body()?.token
                                                        if (token != null) {
                                                            println("Token $token")
                                                            val sharedPrefHelper = SharedPrefHelper(context = navController.context)
                                                            sharedPrefHelper.saveToken(token)
                                                            coroutineScope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Login berhasil",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                                isLoading = false // Matikan loading indicator
                                                                // Navigate to the next screen after successful login
                                                                navController.navigate("home") {
                                                                    popUpTo(0) { inclusive = true }
                                                                }

                                                            }
                                                        } else {

                                                            coroutineScope.launch {
                                                                snackbarHostState.showSnackbar(
                                                                    message = "Login gagal",
                                                                    duration = SnackbarDuration.Short
                                                                )
                                                            }
                                                            isLoading = false // Matikan loading indicator

                                                        }
                                                    } else {

                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Login gagal",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                        }
                                                        isLoading = false // Matikan loading indicator

                                                    }
                                                }

                                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message = "Login error: ${t.message}",
                                                            duration = SnackbarDuration.Short
                                                        )
                                                    }
                                                    isLoading = false // Matikan loading indicator
                                                }
                                            })
                                        }
                                    }
                                },
                                iconResId = null
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tidak punya akun?  ",
                                style = TextStyles.regularTextStyle.copy(
                                    fontSize = 12.sp,
                                    color = kLightColor
                                )
                            )
                            Surface(
                                onClick = {
                                    navController.navigate("register")
                                },
                                color = Color.Transparent
                            ) {
                                Text(
                                    text = "Daftar Sekarang",
                                    style = TextStyles.regularTextStyle.copy(
                                        fontSize = 12.sp,
                                        color = kAccentColor,
                                    )
                                )
                            }
                        }
                    }
                }

                // Tampilkan LoadingAnimation dengan lapisan abu-abu transparan ketika isLoading adalah true
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray.copy(alpha = 0.5f))
                            .wrapContentSize(Alignment.Center)
                    ) {
                        LoadingAnimation()
                    }
                }
            }
        }
    )
}
