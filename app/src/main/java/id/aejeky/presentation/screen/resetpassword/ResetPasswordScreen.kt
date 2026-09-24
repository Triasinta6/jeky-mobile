package id.aejeky.presentation.screen.resetpassword

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import id.aejeky.data.api.ApiClient
import id.aejeky.data.model.ResetPasswordRequest
import id.aejeky.data.model.ForgotPasswordRequest
import id.aejeky.data.model.ForgotPasswordResponse
import id.aejeky.presentation.theme.BorderGray
import id.aejeky.presentation.theme.GoogleRed
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ScreenBackground
import id.aejeky.presentation.theme.TextPlaceholder
import id.aejeky.presentation.theme.TextPrimary
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordScreen(
    email: String,
    onBackClick: () -> Unit,
    onResetSuccess: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var otpError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var apiMessage by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    fun validateResetPassword(): Boolean {
        otpError = ""
        passwordError = ""
        confirmPasswordError = ""
        apiMessage = ""

        var isValid = true

        val cleanOtp = otp.trim()

        if (cleanOtp.isBlank()) {
            otpError = "Kode OTP wajib diisi"
            isValid = false
        } else if (cleanOtp.length != 6 || !cleanOtp.all { it.isDigit() }) {
            otpError = "Kode OTP harus terdiri dari 6 digit"
            isValid = false
        }

        if (password.isBlank()) {
            passwordError = "Kata sandi baru wajib diisi"
            isValid = false
        } else if (password.length < 8) {
            passwordError = "Kata sandi minimal 8 karakter"
            isValid = false
        }

        if (confirmPassword.isBlank()) {
            confirmPasswordError = "Konfirmasi kata sandi wajib diisi"
            isValid = false
        } else if (confirmPassword != password) {
            confirmPasswordError = "Konfirmasi kata sandi tidak sama"
            isValid = false
        }

        return isValid
    }

    fun resetPassword() {
        if (!validateResetPassword()) {
            return
        }

        scope.launch {
            isLoading = true
            apiMessage = ""

            try {
                val response = ApiClient.service.resetPassword(
                    ResetPasswordRequest(
                        email = email.trim(),
                        otp = otp.trim(),
                        password = password,
                        confirmPassword = confirmPassword
                    )
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.success == true) {
                        Toast.makeText(
                            context,
                            body.message.ifBlank {
                                "Kata sandi berhasil diubah."
                            },
                            Toast.LENGTH_SHORT
                        ).show()

                        onResetSuccess()
                    } else {
                        apiMessage =
                            body?.message ?: "Gagal mengubah kata sandi."
                    }
                } else {
                    apiMessage = "Gagal mengubah kata sandi."
                }

            } catch (e: Exception) {
                apiMessage = "Tidak dapat terhubung ke server."
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(scrollState)
            .imePadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(White)
                    .clickable {
                        onBackClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali",
                    tint = TextPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(54.dp))

        Text(
            text = "Buat Kata Sandi Baru",
            color = PrimaryBlue,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Masukkan kode OTP yang telah dikirim ke",
            color = TextSecondary,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = email,
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        ResetInputField(
            value = otp,
            onValueChange = {
                if (it.length <= 6) {
                    otp = it.filter { char -> char.isDigit() }
                }

                otpError = ""
                apiMessage = ""
            },
            title = "Kode OTP",
            placeholder = "Masukkan 6 digit kode OTP",
            errorMessage = otpError,
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResetInputField(
            value = password,
            onValueChange = {
                password = it
                passwordError = ""
                apiMessage = ""
            },
            title = "Kata Sandi Baru",
            placeholder = "Masukkan kata sandi baru",
            errorMessage = passwordError,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            isPasswordVisible = isPasswordVisible,
            trailingIcon = {
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (isPasswordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = "Tampilkan kata sandi",
                        tint = TextPlaceholder
                    )
                }
            },
            helperText = "Minimal 8 karakter"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ResetInputField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = ""
                apiMessage = ""
            },
            title = "Konfirmasi Kata Sandi",
            placeholder = "Ulangi kata sandi baru",
            errorMessage = confirmPasswordError,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            isPasswordVisible = isConfirmPasswordVisible,
            trailingIcon = {
                IconButton(
                    onClick = {
                        isConfirmPasswordVisible = !isConfirmPasswordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (isConfirmPasswordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = "Tampilkan konfirmasi kata sandi",
                        tint = TextPlaceholder
                    )
                }
            }
        )

        if (apiMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = apiMessage,
                modifier = Modifier.fillMaxWidth(),
                color = GoogleRed,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                resetPassword()
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            )
        ) {
            Text(
                text = if (isLoading) {
                    "Memproses..."
                } else {
                    "Ubah Kata Sandi"
                },
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ResetInputField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    placeholder: String,
    errorMessage: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    helperText: String = ""
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextPlaceholder,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = title,
                    tint = TextPlaceholder
                )
            },
            trailingIcon = trailingIcon,
            singleLine = true,
            isError = errorMessage.isNotEmpty(),
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            visualTransformation = if (isPassword && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                errorTextColor = TextPrimary,

                focusedPlaceholderColor = TextPlaceholder,
                unfocusedPlaceholderColor = TextPlaceholder,
                errorPlaceholderColor = TextPlaceholder,

                focusedLeadingIconColor = TextPlaceholder,
                unfocusedLeadingIconColor = TextPlaceholder,
                errorLeadingIconColor = TextPlaceholder,

                focusedTrailingIconColor = TextPlaceholder,
                unfocusedTrailingIconColor = TextPlaceholder,
                errorTrailingIconColor = TextPlaceholder,

                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = BorderGray,
                errorBorderColor = GoogleRed,

                focusedContainerColor = White,
                unfocusedContainerColor = White,
                errorContainerColor = White,

                cursorColor = PrimaryBlue,
                errorCursorColor = GoogleRed
            )
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = errorMessage,
                color = GoogleRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        } else if (helperText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = helperText,
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
    }
}