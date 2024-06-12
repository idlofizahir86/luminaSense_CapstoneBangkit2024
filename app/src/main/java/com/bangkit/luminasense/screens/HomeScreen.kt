package com.bangkit.luminasense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.luminasense.components.CustomItemCard
import com.bangkit.luminasense.ui.theme.LuminaSenseTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LuminaSenseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hallow,",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Bangkit User",
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
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
                    onTap = { /* Handle card tap */ },
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LuminaSenseTheme {
        HomeScreen()
    }
}
