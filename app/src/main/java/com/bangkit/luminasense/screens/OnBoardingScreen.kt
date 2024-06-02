package com.bangkit.luminasense.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bangkit.luminasense.R
import com.bangkit.luminasense.components.CustomButton
import com.bangkit.luminasense.ui.theme.kAccentColor
import com.bangkit.luminasense.ui.theme.kDarkColor
import com.bangkit.luminasense.ui.theme.kLightColor


@Composable
fun OnBoardingScreen(navController: NavController) {


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
                    Image(
                        painter = painterResource(id = R.drawable.logo_splash),
                        contentDescription = "Logo Splash",
                        modifier = Modifier
                            .height(259.dp)
                            .width(307.dp)
                    )
                    Spacer(modifier = Modifier.height(48.dp),)
                    Box (
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ){
                        CustomButton(
                            title = "LOGIN",
                            color = kAccentColor,
                            titleColor = kLightColor,
                            onTap = {
                                navController.navigate("login")
                                    },
                            iconResId = null  // Atau tidak menentukan ikon
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp),)
                    Box (
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ){
                        CustomButton(
                            title = "REGISTER",
                            color = kAccentColor,
                            titleColor = kLightColor,
                            onTap = {
                                navController.navigate("register")
                            },
                            iconResId = null  // Atau tidak menentukan ikon
                        )
                    }
                    Spacer(modifier = Modifier.height(48.dp),)

                    Image(
                        painter = painterResource(id = R.drawable.logo_bangkit),
                        contentDescription = "Logo Bangkit",
                        modifier = Modifier
                            .height(68.dp)
                            .width(256.dp)
                    )

                }
            }
        }
    )
}
