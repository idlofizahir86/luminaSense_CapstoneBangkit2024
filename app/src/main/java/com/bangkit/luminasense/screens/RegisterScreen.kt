package com.bangkit.luminasense.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bangkit.luminasense.R
import com.bangkit.luminasense.components.CustomButton
import com.bangkit.luminasense.components.CustomTextFormField
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kAccentColor
import com.bangkit.luminasense.ui.theme.kDarkColor
import com.bangkit.luminasense.ui.theme.kLightColor

@Composable
fun RegisterScreen(navController: NavController) {
    Scaffold(
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = kDarkColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "REGISTER", style = TextStyles.boldTextStyle.copy(
                        fontSize = 20.sp,
                        color = kLightColor,
                    ))
                    Spacer(modifier = Modifier.height(48.dp),)
                    var namaText by remember { mutableStateOf("") }
                    CustomTextFormField(
                        title = "Nama*",
                        hintText = "Masukkan nama anda",
                        value = namaText,
                        isPassword = false)

                    var emailText by remember { mutableStateOf("") }
                    CustomTextFormField(
                        title = "Email*",
                        hintText = "Masukkan email anda",
                        value = emailText,
                        isPassword = false)

                    var passText by remember { mutableStateOf("") }
                    CustomTextFormField(
                        title = "Password*",
                        hintText = "Masukkan password anda",
                        value = passText,
                        isPassword = true)
                    var passConfirmText by remember { mutableStateOf("") }
                    CustomTextFormField(
                        title = "Konfirmasi Password*",
                        hintText = "Ulangi password anda",
                        value = passConfirmText,
                        isPassword = true)
                    Spacer(modifier = Modifier.height(48.dp),)
                    Box (
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ){
                        CustomButton(
                            title = "Register",
                            color = kAccentColor,
                            titleColor = kLightColor,
                            onTap = { /* Tindakan saat tombol diklik */ },
                            iconResId = null  // Atau tidak menentukan ikon
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp),)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Sudah punya akun?  ",
                            style = TextStyles.regularTextStyle.copy(
                                fontSize = 12.sp,
                                color = kLightColor
                            ))
                        Surface (
                            onClick = {
                                navController.navigate("login")
                            },
                            color = Color.Transparent
                        ){
                            Text(text = "Login Sekarang",
                                style = TextStyles.regularTextStyle.copy(
                                    fontSize = 12.sp,
                                    color = kAccentColor,
                                ))
                        }
                    }


                }
            }
        }
    )
}
