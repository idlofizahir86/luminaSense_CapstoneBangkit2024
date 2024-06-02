package com.bangkit.luminasense.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.luminasense.ui.theme.TextStyles
import androidx.compose.ui.unit.Dp
import com.bangkit.luminasense.ui.theme.kMainColor

@Composable
fun CustomButton(
    title: String,
    width: Dp = Dp.Infinity,
    color: Color,
    titleColor: Color,
    iconResId: Int?, // Gunakan Int? untuk menyatakan bahwa ID drawable adalah opsional
    onTap: () -> Unit
) {
    Surface(
        onClick = onTap,
        modifier = Modifier.fillMaxWidth(),
        color = color,
        shape = RoundedCornerShape(50.dp)
    ) {
        Row(
            modifier = Modifier
                .width(width)
                .height(50.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (iconResId != null) Arrangement.SpaceBetween else Arrangement.Center

        ) {
            Text(
                text = title,
                style = TextStyles.boldTextStyle.copy(
                    fontSize = 16.sp,
                    color = titleColor,
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            if (iconResId != null) {
                // Tampilkan ikon jika ada
                val icon = painterResource(iconResId)
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    alignment = Alignment.Center
                )
            }


        }
    }
}


@Preview
@Composable
fun PreviewCustomButton() {
    CustomButton(
        title = "Button without icon",
        color = kMainColor,
        titleColor = Color.White,
        onTap = { /* Tindakan saat tombol diklik */ },
        iconResId = null  // Atau tidak menentukan ikon
    )
}