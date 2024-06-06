package com.bangkit.luminasense.ui.theme

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

object TextStyles {
    val boldTextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black // Anda juga dapat menambahkan properti lain seperti warna di sini
    )

    val semiBoldTextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.Black
    )

    val mediumTextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Color.Black
    )

    val regularTextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.Black
    )

    val lightTextStyle = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        color = Color.Black
    )
}
