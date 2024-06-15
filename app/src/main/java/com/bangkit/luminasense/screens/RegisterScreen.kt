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
import com.bangkit.luminasense.backend.service.ApiConfig
import com.bangkit.luminasense.backend.service.RegisterRequest
import com.bangkit.luminasense.backend.service.RegisterResponse
import com.bangkit.luminasense.components.CustomButton
import com.bangkit.luminasense.components.CustomTextFormField
import com.bangkit.luminasense.components.LoadingAnimation
import com.bangkit.luminasense.components.ValidationType
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kAccentColor
import com.bangkit.luminasense.ui.theme.kDarkColor
import com.bangkit.luminasense.ui.theme.kLightColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val (name, setName) = rememberSaveable {
        mutableStateOf("")
    }
    var (email, setEmail) = remember {
        mutableStateOf("")
    }
    val (pass, setPass) = rememberSaveable {
        mutableStateOf("")
    }
    val (passConfirm, setPassConfirm) = rememberSaveable {
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
                            text = "REGISTER",
                            style = TextStyles.boldTextStyle.copy(
                                fontSize = 20.sp,
                                color = kLightColor,
                            )
                        )
                        Spacer(modifier = Modifier.height(48.dp))

                        CustomTextFormField(
                            title = "Nama*",
                            hintText = "Masukkan nama anda",
                            value = name,
                            onValueChange = setName,
                            validationType = ValidationType.Name,
                            isPassword = false
                        )

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

                        CustomTextFormField(
                            title = "Konfirmasi Password*",
                            hintText = "Ulangi password anda",
                            value = passConfirm,
                            onValueChange = setPassConfirm,
                            validationType = ValidationType.PasswordConfirmation,
                            isPassword = true,
                            passwordToMatch = pass
                        )

                        Spacer(modifier = Modifier.height(48.dp))

                        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                            CustomButton(
                                title = "Register",
                                color = kAccentColor,
                                titleColor = kLightColor,
                                onTap = {
                                    if (!isLoading) {
                                        isLoading = true
                                        println("Nama: ${name}, Email: $email, Password: $pass, Konfirmasi Password: $passConfirm")
                                        coroutineScope.launch {
                                            val apiService = ApiConfig.getApiService()
                                            val registerRequest = RegisterRequest(name, email, pass, passConfirm)
                                            apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
                                                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                                                    if (response.isSuccessful) {
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Registrasi berhasil",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                            isLoading = false // Matikan loading indicator
                                                            navController.navigate("login")
                                                        }
                                                    } else {
                                                        coroutineScope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Registrasi gagal",
                                                                duration = SnackbarDuration.Short
                                                            )
                                                            isLoading = false // Matikan loading indicator
                                                        }
                                                    }

                                                }

                                                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message = "Registrasi error: ${t.message}",
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
                                text = "Sudah punya akun?  ",
                                style = TextStyles.regularTextStyle.copy(
                                    fontSize = 12.sp,
                                    color = kLightColor
                                )
                            )
                            Surface(
                                onClick = {
                                    navController.navigate("login")
                                },
                                color = Color.Transparent
                            ) {
                                Text(
                                    text = "Login Sekarang",
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

