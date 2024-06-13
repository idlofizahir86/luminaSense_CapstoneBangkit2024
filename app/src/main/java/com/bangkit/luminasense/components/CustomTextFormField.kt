package com.bangkit.luminasense.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.luminasense.ui.theme.TextStyles
import com.bangkit.luminasense.ui.theme.kSilverColor
import com.bangkit.luminasense.ui.theme.kWhiteColor
import kotlinx.coroutines.launch

@Composable
fun CustomTextFormField(
    title: String,
    hintText: String,
    value: String,
    validationType: ValidationType,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit,
    passwordToMatch: String? = null
) {
    var isObscured by remember { mutableStateOf(isPassword) }
    var isTouched by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var error by remember { mutableStateOf<String?>(null) }
    val focusManager = LocalFocusManager.current

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
            val textFieldModifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)

            TextField(
                shape = RoundedCornerShape(14.dp),
                value = value,
                onValueChange = {
                    val updatedValue = if (validationType == ValidationType.Name) it else it.trim()
                    onValueChange(updatedValue)
                    if (!isTouched) {
                        isTouched = true
                    }
                    error = validateText(updatedValue, validationType, passwordToMatch)
                },
                modifier = textFieldModifier,
                placeholder = { Text(hintText, color = kSilverColor) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = kWhiteColor,
                    unfocusedContainerColor = kWhiteColor,
                    focusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = if (isObscured) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (isPassword) ImeAction.Done else ImeAction.Next,
                    keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(focusDirection = androidx.compose.ui.focus.FocusDirection.Next) },
                    onDone = { focusManager.clearFocus() }
                ),
                trailingIcon = {
                    if (isPassword) {
                        IconButton(onClick = { isObscured = !isObscured }) {
                            val icon = if (isObscured) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Icon(icon, contentDescription = "Toggle visibility")
                        }
                    }
                },
                singleLine = true
            )
        }
        if (isTouched && error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                style = TextStyle(color = MaterialTheme.colorScheme.error)
            )
        }
    }
}

enum class ValidationType {
    Name,
    Email,
    Password,
    PasswordConfirmation
}

private fun validateText(text: String, validationType: ValidationType, passwordToMatch: String?): String? {
    return when (validationType) {
        ValidationType.Name -> if (text.isNotEmpty() && text.length < 2) "Nama minimal2 karakter" else null
        ValidationType.Email -> if (!text.contains("@")) "Format email salah" else null
        ValidationType.Password -> if (text.isNotEmpty() && text.length < 8) "Password minimal 8 Karaketer" else null
        ValidationType.PasswordConfirmation -> if (passwordToMatch != null && text != passwordToMatch) "Password tidak sama" else null
    }
}

@Preview
@Composable
fun PreviewCustomTextFormField() {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column {
        CustomTextFormField(
            title = "Password",
            hintText = "Enter your password",
            value = password,
            validationType = ValidationType.Password,
            isPassword = true,
            onValueChange = { password = it }
        )
        CustomTextFormField(
            title = "Confirm Password",
            hintText = "Confirm your password",
            value = confirmPassword,
            validationType = ValidationType.PasswordConfirmation,
            isPassword = true,
            onValueChange = { confirmPassword = it },
            passwordToMatch = password
        )
    }
}
