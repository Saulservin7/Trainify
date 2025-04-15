package com.servin.trainify.auth.presentation.components


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.servin.trainify.R

@Composable
fun FieldForm(
    title: String = "",
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    errorDescription: String = "",
    isError: Boolean = false,
    isPassword: Boolean = false,
    passwordHidden: Boolean = false,
    onPasswordVisibilityToggle: () -> Unit = {},
    visibilityIcon: Int = if (passwordHidden) R.drawable.passwordvisible else R.drawable.passwordoff,
    modifier: Modifier = Modifier
) {
    Text(text = title, modifier = Modifier.padding(bottom = 10.dp))
    if (isError) {
        Text(
            text = errorDescription,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 5.dp)
        )
    }
    TextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        modifier = modifier,
        textStyle = if (isError) TextStyle(color = Color.Red) else TextStyle.Default,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { onPasswordVisibilityToggle() }) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(id = visibilityIcon),
                        contentDescription = null
                    )
                }
            }
        }
    )
}