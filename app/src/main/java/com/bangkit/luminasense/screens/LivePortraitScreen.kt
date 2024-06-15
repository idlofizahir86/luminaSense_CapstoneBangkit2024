package com.bangkit.luminasense.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bangkit.luminasense.backend.preferences.SharedPrefHelper
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

@Composable
fun LivePortraitScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefHelper = SharedPrefHelper(context)
    val ipAddress = sharedPrefHelper.getIpAddress()

    if (ipAddress != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val player = remember {
                SimpleExoPlayer.Builder(context).build().apply {
                    val mediaItem = MediaItem.fromUri("http://192.168.0.7/stream.m3u8")
                    setMediaItem(mediaItem)
                    prepare()
                    playWhenReady = true
                }
            }

            AndroidView(
                factory = {
                    PlayerView(it).apply {
                        this.player = player
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                        useController = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "LuminaSense",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Keterangan:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "IP Address: $ipAddress",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ada 4 orang pada gambar\nLampu dinyalakan",
                fontSize = 16.sp
            )
        }
    } else {
        // Handle the case where IP address is not available
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tidak ada IP Adress yang tersimpan",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = {
                navController.navigate("ipInput")
            }) {
                Text("Set IP Address")
            }
        }
    }
}
