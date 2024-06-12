package com.bangkit.luminasense.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.luminasense.R
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kDarkColor

@Composable
fun CustomItemCard(
    title: String,
    description: String,
    color: Color,
    titleColor: Color,
    descriptionColor: Color = Color.Gray,
    iconResId: Int?, // Gunakan Int? untuk menyatakan bahwa ID drawable adalah opsional
    onTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onTap,
        modifier = modifier
            .padding(8.dp)
            .border(width = 1.dp, color = kDarkColor, shape = RoundedCornerShape(12.dp)),
        color = color,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (iconResId != null) {
                val icon = painterResource(iconResId)
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 16.dp),
                    alignment = Alignment.Center
                )
            }

            Text(
                text = title,
                style = TextStyles.boldTextStyle.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = description,
                style = TextStyles.regularTextStyle.copy(
                    fontSize = 14.sp,
                    color = descriptionColor,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
