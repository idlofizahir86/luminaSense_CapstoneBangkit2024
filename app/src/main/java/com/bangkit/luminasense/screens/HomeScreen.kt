package com.bangkit.luminasense.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.auth0.android.jwt.JWT
import com.bangkit.luminasense.components.CustomItemCard
import com.bangkit.luminasense.R
import com.bangkit.luminasense.backend.preferences.SharedPrefHelper
import com.bangkit.luminasense.components.CustomButton
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kAccentColor
import com.bangkit.luminasense.ui.theme.kBlackColor
import com.bangkit.luminasense.ui.theme.kLightColor
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefHelper = SharedPrefHelper(context)
    val token = sharedPrefHelper.getToken()
    var userName by remember { mutableStateOf("Bangkit User") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                token?.let {
                    val jwt = JWT(it)
                    val name = jwt.getClaim("user").asObject(User::class.java)?.name
                    name?.let {
                        userName = it
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeScreen", "Failed to decode token", e)
            }
        }
    }

    Scaffold(
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = kLightColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start

                ) {
                    Text(
                        text = "Hallow,",
                        style = TextStyles.boldTextStyle.copy(
                            fontSize = 24.sp,

                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .padding(start = 8.dp),
                    )
                    Text(
                        text = userName,
                        style = TextStyles.mediumTextStyle.copy(
                            fontSize = 20.sp,
                            color = kBlackColor,
                        ),
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .padding(start = 8.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val cardModifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(0.45f) // Adjust the width as needed

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CustomItemCard(
                                title = "Live Camera Portrait",
                                description = "Lihat Live Camera dalam Orientasi Vertikal atau Portrait",
                                color = Color.White,
                                titleColor = Color.Black,
                                descriptionColor = Color.Gray,
                                iconResId = R.drawable.ic_phone_portrait,
                                onTap = { navController.navigate("livePortrait")  },
                                modifier = cardModifier
                            )
                            CustomItemCard(
                                title = "Live Camera Landscape",
                                description = "Lihat Live Camera dalam Orientasi Horizontal atau Landscape",
                                color = Color.White,
                                titleColor = Color.Black,
                                descriptionColor = Color.Gray,
                                iconResId = R.drawable.ic_phone_landscape,
                                onTap = { /* Handle card tap */ },
                                modifier = cardModifier
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CustomItemCard(
                                title = "Profile",
                                description = "Lihat Profile dan pengaturan akun",
                                color = Color.White,
                                titleColor = Color.Black,
                                descriptionColor = Color.Gray,
                                iconResId = R.drawable.ic_person,
                                onTap = { /* Handle card tap */ },
                                modifier = cardModifier
                            )
                            CustomItemCard(
                                title = "Petunjuk",
                                description = "Pelajari cara menggunakan LuminaSense",
                                color = Color.White,
                                titleColor = Color.Black,
                                descriptionColor = Color.Gray,
                                iconResId = R.drawable.ic_info,
                                onTap = { /* Handle card tap */ },
                                modifier = cardModifier
                            )
                        }
                        CustomButton(
                            title = "Button Logout Sementara",
                            color = kAccentColor,
                            titleColor = kLightColor,
                            iconResId = null,
                            onTap = {
                                sharedPrefHelper.clearToken()
                                navController.navigate("onBoarding") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )


                    }
                }
            }
        }
    )
}

data class User(
    val id: String,
    val name: String
)
