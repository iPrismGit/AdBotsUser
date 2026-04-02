package com.iprism.adbotsuser.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.iprism.adbotsuser.presentation.ui.theme.DarkBlue
import com.iprism.adbotsuser.R
import com.iprism.adbotsuser.presentation.ui.components.CustomTextField
import com.iprism.adbotsuser.presentation.ui.theme.BorderGrey
import com.iprism.adbotsuser.presentation.ui.theme.Grey555
import com.iprism.adbotsuser.presentation.ui.theme.LightGrey

@Composable
fun LoginScreen(onContinueClick: () -> Unit) {

    var userId by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "AdBots Logo",
                modifier = Modifier.size(180.dp)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .border(1.dp, BorderGrey, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = Color.White,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "User Id",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    userId,
                    "Enter Id",
                    KeyboardType.Text
                ) { userId = it }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Password",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter Password", style = MaterialTheme.typography.bodySmall, color = Grey555) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = LightGrey
                    ),
                    visualTransformation = if (isPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(
                            painter = if (isPasswordVisible)
                                painterResource(R.drawable.visible)
                            else
                                painterResource(R.drawable.visibility_off),
                            contentDescription = "Toggle Password",
                            modifier = Modifier.clickable {
                                isPasswordVisible = !isPasswordVisible
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "By clicking, I accept the terms of service and privacy policy",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onContinueClick() },
                    modifier = Modifier
                        .fillMaxWidth().imePadding(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                ) {
                    Text(
                        text = "Continue",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
