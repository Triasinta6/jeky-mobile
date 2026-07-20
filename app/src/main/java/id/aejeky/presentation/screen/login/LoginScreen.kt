package id.aejeky.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.R
import id.aejeky.data.api.ApiClient
import id.aejeky.data.local.SessionManager
import id.aejeky.data.model.MobileLoginRequest
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
fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var emailOrPhoneError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var apiMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sessionManager = remember {
        SessionManager(context)
    }

    val scope = rememberCoroutineScope()

    fun validateLogin(): Boolean {
        emailOrPhoneError = ""
        passwordError = ""
        apiMessage = ""

        var isValid = true

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

        return isValid
    }

    fun loginCustomer() {
        if (!validateLogin()) {
            return
        }

        scope.launch {
            isLoading = true

            try {
                val response = ApiClient.service.loginCustomer(
                    MobileLoginRequest(
                        emailOrPhone = emailOrPhone,
                        password = password
                    )
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.success == true) {
                        sessionManager.saveLoginSession(
                            token = body.token ?: "",
                            customerId = body.customerId ?: -1L,
                            name = body.name ?: "",
                            email = body.email,
                            noHp = body.noHp
                        )

                        onLoginClick()
                    } else {
                        apiMessage = body?.message ?: "Login gagal"
                    }
                } else {
                    apiMessage = "Login gagal. Coba lagi."
                }
            } catch (e: Exception) {
                apiMessage = e.message ?: "Tidak dapat terhubung ke server"
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
        LoginHeader(
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(18.dp))

        LoginForm(
            emailOrPhone = emailOrPhone,
            onEmailOrPhoneChange = {
                emailOrPhone = it
                emailOrPhoneError = ""
                apiMessage = ""
            },
            password = password,
            onPasswordChange = {
                password = it
                passwordError = ""
                apiMessage = ""
            },
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityClick = {
                isPasswordVisible = !isPasswordVisible
            },
            emailOrPhoneError = emailOrPhoneError,
            passwordError = passwordError,
            apiMessage = apiMessage,
            isLoading = isLoading,
            onLoginClick = {
                loginCustomer()
            },
            onRegisterClick = onRegisterClick,
            onForgotPasswordClick = onForgotPasswordClick
        )
    }
}

@Composable
private fun LoginHeader(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(White)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Kembali",
                    tint = TextPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.login_illustration),
                contentDescription = "Login Illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .padding(top = 8.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Masuk ke Jeky",
            modifier = Modifier.fillMaxWidth(),
            color = PrimaryBlue,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Satu aplikasi untuk semua perjalananmu.",
            modifier = Modifier.fillMaxWidth(),
            color = TextSecondary,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoginForm(
    emailOrPhone: String,
    onEmailOrPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityClick: () -> Unit,
    emailOrPhoneError: String,
    passwordError: String,
    apiMessage: String,
    isLoading: Boolean,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LoginInputField(
            value = emailOrPhone,
            onValueChange = onEmailOrPhoneChange,
            title = "Nomor HP atau Email",
            placeholder = "Masukkan nomor HP atau email",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nomor HP atau Email",
                    tint = TextPlaceholder
                )
            },
            errorMessage = emailOrPhoneError
        )

        Spacer(modifier = Modifier.height(14.dp))

        LoginInputField(
            value = password,
            onValueChange = onPasswordChange,
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
                    onClick = onPasswordVisibilityClick
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

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Lupa kata sandi?",
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onForgotPasswordClick()
                },
            color = PrimaryBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (apiMessage.isNotEmpty()) {
            Text(
                text = apiMessage,
                modifier = Modifier.fillMaxWidth(),
                color = GoogleRed,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        LoginButton(
            onClick = onLoginClick,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(18.dp))

        LoginDivider()

        Spacer(modifier = Modifier.height(14.dp))

        GoogleLoginButton()

        Spacer(modifier = Modifier.height(18.dp))

        RegisterSection(
            onRegisterClick = onRegisterClick
        )
    }
}

@Composable
private fun LoginInputField(
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

@Composable
private fun LoginButton(
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
            text = if (isLoading) "Memproses..." else "Masuk",
            color = White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LoginDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(BorderGray)
        )

        Text(
            text = "atau",
            modifier = Modifier.padding(horizontal = 18.dp),
            color = TextPlaceholder,
            fontSize = 14.sp
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(BorderGray)
        )
    }
}

@Composable
private fun GoogleLoginButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(White)
            .border(
                width = 1.dp,
                color = BorderGray,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable {
                // nanti Google sign-in
            }
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "G",
            color = GoogleRed,
            fontSize = 23.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.size(14.dp))

        Text(
            text = "Masuk dengan Google",
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RegisterSection(
    onRegisterClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Belum punya akun? ",
            color = TextSecondary,
            fontSize = 14.sp
        )

        Text(
            text = "Daftar sekarang",
            modifier = Modifier.clickable {
                onRegisterClick()
            },
            color = PrimaryBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}