package id.aejeky.presentation.screen.forgotpassword

import kotlinx.coroutines.launch
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.api.ApiClient
import id.aejeky.data.model.ForgotPasswordRequest
import id.aejeky.presentation.theme.BorderGray
import id.aejeky.presentation.theme.GoogleRed
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ScreenBackground
import id.aejeky.presentation.theme.TextPlaceholder
import id.aejeky.presentation.theme.TextPrimary
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White

@Composable
fun ForgotPasswordScreen(
    onBackToLoginClick: () -> Unit,
    onOtpSent: (String) -> Unit
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var emailOrPhoneError by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    var apiMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    fun validateForgotPassword(): Boolean  {
        emailOrPhoneError = ""
        successMessage = ""

        val cleanEmailOrPhone = emailOrPhone.trim()

        if (cleanEmailOrPhone.isBlank()) {
            emailOrPhoneError = "Nomor HP atau Email wajib diisi"
                return false
        }

        return true
    }

    fun sendForgotPassword() {
        if (!validateForgotPassword()) {
            return
        }

        scope.launch {
            isLoading = true
            apiMessage = ""
            successMessage = ""

            try {
                val response = ApiClient.service.forgotPassword(
                    ForgotPasswordRequest(
                        email = emailOrPhone.trim()
                    )
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.success == true) {
                        onOtpSent(emailOrPhone.trim())
                    } else {
                        apiMessage =
                            body?.message ?: "Gagal mingirim intruksi pemulihan."
                    }
                } else {
                    apiMessage = "Gagal mengirim intruksi pemulihan."
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
            .imePadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        ForgotPasswordTopBar(
            onBackToLoginClick = onBackToLoginClick
        )

        Spacer(modifier = Modifier.height(70.dp))

        ForgotPasswordHeader()

        Spacer(modifier = Modifier.height(34.dp))

        ForgotPasswordInputField(
            value = emailOrPhone,
            onValueChange = {
                emailOrPhone = it
                emailOrPhoneError = ""
                successMessage = ""
            },
            errorMessage = emailOrPhoneError
        )

        if (successMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = successMessage,
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryBlue,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        if (apiMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = apiMessage,
                modifier = Modifier.fillMaxWidth(),
                color = GoogleRed,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        SendInstructionButton(
            onClick = {
                sendForgotPassword()
            },
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(22.dp))

        BackToLoginSection(
            onBackToLoginClick = onBackToLoginClick
        )
    }
}

@Composable
private fun ForgotPasswordTopBar(
    onBackToLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(White)
                .clickable {
                    onBackToLoginClick()
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
}

@Composable
private fun ForgotPasswordHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lupa Kata Sandi?",
            color = PrimaryBlue,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Masukkan nomor HP atau email yang terhubung dengan akun Jeky kamu.",
            color = TextSecondary,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ForgotPasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Nomor HP atau Email",
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
                    text = "Masukkan nomor HP atau email",
                    color = TextPlaceholder,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Nomor HP atau Email",
                    tint = TextPlaceholder
                )
            },
            singleLine = true,
            isError = errorMessage.isNotEmpty(),
            shape = RoundedCornerShape(14.dp),
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
        }
    }
}

@Composable
private fun SendInstructionButton(
    onClick: () -> Unit,
    isLoading: Boolean
) {
    Button(
        onClick = onClick,
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
            text = if (isLoading) "Mengirim..." else "Kirim Instruksi",
            color = White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BackToLoginSection(
    onBackToLoginClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ingat kata sandi? ",
            color = TextSecondary,
            fontSize = 14.sp
        )

        Text(
            text = "Masuk",
            modifier = Modifier.clickable {
                onBackToLoginClick()
            },
            color = PrimaryBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}