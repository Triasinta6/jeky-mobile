package id.aejeky.presentation.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.presentation.theme.BorderGray
import id.aejeky.presentation.theme.GoogleRed
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ScreenBackground
import id.aejeky.presentation.theme.TextPlaceholder
import id.aejeky.presentation.theme.TextPrimary
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White

@Composable
fun RegisterScreen(
    onBackToLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var fullNameError by remember { mutableStateOf("") }
    var emailOrPhoneError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    fun validateRegister() {
        fullNameError = ""
        emailOrPhoneError = ""
        passwordError = ""
        confirmPasswordError = ""

        var isValid = true

        if (fullName.isBlank()) {
            fullNameError = "Nama lengkap wajib diisi"
            isValid = false
        }

        if (emailOrPhone.isBlank()) {
            emailOrPhoneError = "Nomor HP atau Email wajib diisi"
            isValid = false
        }

        if (password.isBlank()) {
            passwordError = "Kata sandi wajib diisi"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Kata sandi minimal 6 karakter"
            isValid = false
        }

        if (confirmPassword.isBlank()) {
            confirmPasswordError = "Konfirmasi kata sandi wajib diisi"
            isValid = false
        } else if (confirmPassword != password) {
            confirmPasswordError = "Konfirmasi kata sandi tidak sama"
            isValid = false
        }

        if (isValid) {
            onRegisterClick()
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
        Spacer(modifier = Modifier.height(34.dp))

        RegisterHeader()

        Spacer(modifier = Modifier.height(26.dp))

        RegisterInputField(
            value = fullName,
            onValueChange = {
                fullName = it
                fullNameError = ""
            },
            title = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nama Lengkap",
                    tint = TextPlaceholder
                )
            },
            errorMessage = fullNameError
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterInputField(
            value = emailOrPhone,
            onValueChange = {
                emailOrPhone = it
                emailOrPhoneError = ""
            },
            title = "Nomor HP atau Email",
            placeholder = "Masukkan nomor HP atau email",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Nomor HP atau Email",
                    tint = TextPlaceholder
                )
            },
            errorMessage = emailOrPhoneError
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterInputField(
            value = password,
            onValueChange = {
                password = it
                passwordError = ""
            },
            title = "Kata Sandi",
            placeholder = "Masukkan kata sandi",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Kata Sandi",
                    tint = TextPlaceholder
                )
            },
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
            isPassword = true,
            isPasswordVisible = isPasswordVisible,
            errorMessage = passwordError
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterInputField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = ""
            },
            title = "Konfirmasi Kata Sandi",
            placeholder = "Ulangi kata sandi",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Konfirmasi Kata Sandi",
                    tint = TextPlaceholder
                )
            },
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
            },
            isPassword = true,
            isPasswordVisible = isConfirmPasswordVisible,
            errorMessage = confirmPasswordError
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                validateRegister()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            )
        ) {
            Text(
                text = "Daftar",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sudah punya akun? ",
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
}

@Composable
private fun RegisterHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Daftar Akun",
            color = PrimaryBlue,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Buat akun baru untuk mulai menggunakan layanan Jeky.",
            color = TextSecondary,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RegisterInputField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    placeholder: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    errorMessage: String = ""
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
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            singleLine = true,
            isError = errorMessage.isNotEmpty(),
            shape = RoundedCornerShape(14.dp),
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
        }
    }
}