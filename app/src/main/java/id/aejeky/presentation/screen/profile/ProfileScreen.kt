package id.aejeky.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.api.ApiClient
import id.aejeky.data.local.SessionManager
import id.aejeky.data.model.ProfileRequest
import id.aejeky.presentation.theme.BorderGray
import id.aejeky.presentation.theme.ErrorRed
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ScreenBackground
import id.aejeky.presentation.theme.SuccessGreen
import id.aejeky.presentation.theme.TextPlaceholder
import id.aejeky.presentation.theme.TextPrimary
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = remember {
        SessionManager(context)
    }

    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf(sessionManager.getName()) }
    var email by remember { mutableStateOf(sessionManager.getEmail()) }
    var noHp by remember { mutableStateOf(sessionManager.getNoHp()) }
    var address by remember { mutableStateOf(sessionManager.getAddress()) }

    var nameError by remember { mutableStateOf("") }
    var noHpError by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isSuccessMessage by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun updateProfile() {
        nameError = ""
        noHpError = ""
        message = ""
        isSuccessMessage = false

        var isValid = true

        if (name.isBlank()) {
            nameError = "Nama wajib diisi"
            isValid = false
        }

        if (noHp.isBlank()) {
            noHpError = "Nomor HP wajib diisi"
            isValid = false
        }

        if (!isValid) {
            return
        }

        scope.launch {
            isLoading = true

            try {
                val response = ApiClient.service.updateCustomerProfile(
                    customerId = sessionManager.getCustomerId(),
                    request = ProfileRequest(
                        name = name,
                        noHp = noHp,
                        address = address
                    )
                )

                if (response.success) {
                    name = response.name ?: name
                    email = response.email ?: email
                    noHp = response.noHp ?: noHp
                    address = response.address ?: address

                    sessionManager.updateProfileSession(
                        name = name,
                        noHp = noHp,
                        address = address
                    )

                    isSuccessMessage = true
                    message = response.message
                } else {
                    isSuccessMessage = false
                    message = response.message
                }
            } catch (e: Exception) {
                isSuccessMessage = false
                message = e.message ?: "Gagal memperbarui profil"
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
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                tint = TextPrimary,
                modifier = Modifier.clickable {
                    onBackClick()
                }
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Text(
                text = "Profil Saya",
                color = TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = "Logout",
                tint = ErrorRed,
                modifier = Modifier.clickable {
                    onLogoutClick()
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = White,
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = PrimaryBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Lengkapi Profil",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Nomor HP diperlukan agar kamu bisa melakukan order.",
                color = TextSecondary,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileInputField(
            value = name,
            onValueChange = {
                name = it
                nameError = ""
                message = ""
            },
            title = "Nama Lengkap",
            placeholder = "Masukkan nama lengkap",
            errorMessage = nameError
        )

        Spacer(modifier = Modifier.height(14.dp))

        ProfileInputField(
            value = email,
            onValueChange = {
                email = it
            },
            title = "Email",
            placeholder = "Email belum tersedia",
            enabled = false
        )

        Spacer(modifier = Modifier.height(14.dp))

        ProfileInputField(
            value = noHp,
            onValueChange = {
                noHp = it
                noHpError = ""
                message = ""
            },
            title = "Nomor HP",
            placeholder = "Masukkan nomor HP",
            errorMessage = noHpError
        )

        Spacer(modifier = Modifier.height(14.dp))

        ProfileInputField(
            value = address,
            onValueChange = {
                address = it
                message = ""
            },
            title = "Alamat",
            placeholder = "Masukkan alamat"
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (isSuccessMessage) SuccessGreen else ErrorRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                updateProfile()
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            )
        ) {
            Text(
                text = if (isLoading) "Menyimpan..." else "Simpan Profil",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ProfileInputField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    placeholder: String,
    enabled: Boolean = true,
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
            enabled = enabled,
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
            singleLine = true,
            isError = errorMessage.isNotEmpty(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                disabledTextColor = TextSecondary,
                errorTextColor = TextPrimary,

                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = BorderGray,
                disabledBorderColor = BorderGray,
                errorBorderColor = ErrorRed,

                focusedContainerColor = White,
                unfocusedContainerColor = White,
                disabledContainerColor = White,
                errorContainerColor = White,

                cursorColor = PrimaryBlue,
                errorCursorColor = ErrorRed
            )
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = errorMessage,
                color = ErrorRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}