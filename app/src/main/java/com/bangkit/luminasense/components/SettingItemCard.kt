package com.bangkit.luminasense.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kBlackColor
import com.bangkit.luminasense.ui.theme.kSilverColor
import com.bangkit.luminasense.ui.theme.kWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItemCard(title: String, onTap: () -> Unit) {
    Surface(
        onClick = onTap,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, kSilverColor)
    ) {
        Text(
            text = title,
            style = TextStyles.boldTextStyle.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = kBlackColor
            ),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 23.dp)
        )
    }

}
