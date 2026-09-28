package id.aejeky.data.model

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val newPassword: String,
    val confirmPassword: String
)