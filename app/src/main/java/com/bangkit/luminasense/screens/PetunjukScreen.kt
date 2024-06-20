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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
import com.bangkit.luminasense.ui.theme.kBlackColor
import com.bangkit.luminasense.ui.theme.kDarkColor
import com.bangkit.luminasense.ui.theme.kLightColor
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PetunjukScreen(navController: NavController) {

    Scaffold(

        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = kLightColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "PETUNJUK",
                            style = TextStyles.boldTextStyle.copy(
                                fontSize = 20.sp,
                                color = kBlackColor,
                            )
                        )
                        Spacer(modifier = Modifier.height(48.dp))

                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun tesPetunjuk(){
    val navController = rememberNavController()
    PetunjukScreen(navController = navController)
}

