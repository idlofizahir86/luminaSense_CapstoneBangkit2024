package com.bangkit.luminasense.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bangkit.luminasense.MainActivity
import com.bangkit.luminasense.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.bangkit.luminasense.ui.theme.LuminaSenseTheme
import com.bangkit.luminasense.ui.theme.kDarkColor
import kotlinx.coroutines.delay

//class SplashScreenActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            LuminaSenseTheme {
//                SplashScreen {
//
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                }
//            }
//        }
//    }
//}


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(3000L)
        navController.navigate("onBoarding")
    }

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
                    Spacer(modifier = Modifier.height(200.dp))
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

