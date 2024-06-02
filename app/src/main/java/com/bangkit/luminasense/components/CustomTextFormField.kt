package com.bangkit.luminasense.components



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kSilverColor

import com.bangkit.luminasense.ui.theme.kWhiteColor

@Composable
fun CustomTextFormField(
    title: String,
    hintText: String,
    value: String,
    isPassword: Boolean = false,
    validator: ((String) -> Boolean)? = null
) {
    var text by remember { mutableStateOf(value) }
    var isObscured by remember { mutableStateOf(isPassword) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = TextStyles.semiBoldTextStyle,
            color = kWhiteColor,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            var modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)

            // Use OutlinedTextField if not password
            if (!isPassword) {
                TextField(
                    shape = RoundedCornerShape(14.dp),
                    value = text,
                    onValueChange = { text = it },
                    modifier = modifier,
                    placeholder = { Text(hintText, color = kSilverColor) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = kWhiteColor,
                        unfocusedContainerColor = kWhiteColor,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
            } else {
                // Use TextField with trailing icon if password
                TextField(
                    shape = RoundedCornerShape(14.dp),

                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = kWhiteColor,
                        unfocusedContainerColor = kWhiteColor,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    modifier = modifier,
                    placeholder = { Text(hintText, color = kSilverColor) },
                    visualTransformation = if (isObscured) {
                        PasswordVisualTransformation()
                    } else {
                        VisualTransformation.None
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isObscured = !isObscured }) {
                            val icon = if (isObscured) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Icon(icon, contentDescription = "Toggle visibility")
                        }
                    }
                )
            }
        }
        validator?.let {
            if (!it(text)) {
                Text(
                    text = "Invalid input",
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(color = MaterialTheme.colorScheme.error)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomTextFormField() {
    var text by remember { mutableStateOf("") }

    CustomTextFormField(
        title = "Password",
        hintText = "Enter your password",
        value = text,
        isPassword = true,
        validator = { it.isNotEmpty() }
    )
}