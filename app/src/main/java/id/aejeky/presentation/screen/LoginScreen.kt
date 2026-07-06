package id.aejeky.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val primaryBlue = Color(0xFF2563EB)
    val darkText = Color(0xFF101828)
    val softText = Color(0xFF667085)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
                    .background(Color(0xFFEFF6FF))
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = darkText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "Jeky",
                        color = primaryBlue,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("Selamat datang\n")
                            append("di ")
                            withStyle(
                                style = SpanStyle(
                                    color = primaryBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Jeky!")
                            }
                        },
                        color = darkText,
                        fontSize = 36.sp,
                        lineHeight = 44.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Masuk untuk melanjutkan ke\nberbagai layanan Jeky.",
                        color = softText,
                        fontSize = 17.sp,
                        lineHeight = 26.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(132.dp)
                        .clip(CircleShape)
                        .background(primaryBlue.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🛵",
                        fontSize = 70.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 30.dp)
            ) {
                LoginTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = "Nomor Handphone",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = "Nomor Handphone",
                            tint = primaryBlue
                        )
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Kata Sandi",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Kata Sandi",
                            tint = primaryBlue
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "Sembunyikan kata sandi",
                            tint = Color(0xFF98A2B3)
                        )
                    },
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Lupa kata sandi?",
                    modifier = Modifier.align(Alignment.End),
                    color = primaryBlue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        // sementara langsung masuk home dulu
                        onLoginClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryBlue
                    )
                ) {
                    Text(
                        text = "Masuk",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color(0xFFE4E7EC))
                    )

                    Text(
                        text = "atau",
                        modifier = Modifier.padding(horizontal = 14.dp),
                        color = Color(0xFF98A2B3),
                        fontSize = 14.sp
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Color(0xFFE4E7EC))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .clickable {
                            // nanti Google sign-in
                        }
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "G",
                        color = Color(0xFFEA4335),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = "Masuk dengan Google",
                        color = darkText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(26.dp))

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Belum punya akun? ",
                        color = softText,
                        fontSize = 15.sp
                    )

                    Text(
                        text = "Daftar di sini",
                        color = primaryBlue,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = label)
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            androidx.compose.ui.text.input.VisualTransformation.None
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF2563EB),
            unfocusedBorderColor = Color(0xFFE4E7EC),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color(0xFF2563EB)
        )
    )
}