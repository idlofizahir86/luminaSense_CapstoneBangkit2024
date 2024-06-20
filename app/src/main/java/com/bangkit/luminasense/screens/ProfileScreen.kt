package com.bangkit.luminasense.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.auth0.android.jwt.JWT
import com.bangkit.luminasense.R
import com.bangkit.luminasense.backend.preferences.SharedPrefHelper
import com.bangkit.luminasense.components.CustomButton
import com.bangkit.luminasense.components.SettingItemCard
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kAccentColor
import com.bangkit.luminasense.ui.theme.kBlackColor
import com.bangkit.luminasense.ui.theme.kLightColor
import com.bangkit.luminasense.ui.theme.kWhiteColor
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController) {
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
                Log.e("ProfileScreen", "Failed to decode token", e)
            }
        }
    }

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
                            .padding(horizontal = 20.dp)
                            .wrapContentSize(Alignment.TopStart),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        ProfileSection(imageUrl = null, userName = userName)

                        SettingItemCard(title = "Chat Us") {
                            
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        SettingItemCard(title = "About Us") {

                        }
                        Spacer(modifier = Modifier.height(60.dp))
                        
                        CustomButton(title = "Keluar", color = kAccentColor, titleColor = kWhiteColor, iconResId = R.drawable.ic_keluar ) {

                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ProfileImage(imageUrl: String?) {
    Image(
        painter = painterResource(id = R.drawable.ic_person), // Placeholder image resource
        contentDescription = null, // Decorative image
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileSection(imageUrl: String?, userName: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .border(1.dp, kBlackColor, CircleShape)
                    .padding(horizontal = 20.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(1.dp, kBlackColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        ProfileImage(imageUrl = null)
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(
                            text = userName,
                            style = TextStyles.boldTextStyle.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Lihat Profil",
                            style = TextStyles.regularTextStyle.copy(
                                fontSize = 16.sp,
                                color = kAccentColor
                            ),
                            modifier = Modifier.clickable {
                                // Navigation logic here to navigate to profile view
                            }
                        )
                    }
                }
            }
        }
    }
}




//@Preview
//@Composable
//fun testProf(){
//    ProfileScreen()
//}