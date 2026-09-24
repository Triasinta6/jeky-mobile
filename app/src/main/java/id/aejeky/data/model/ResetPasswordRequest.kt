package id.aejeky.data.model

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val password: String,
    val confirmPassword: String
)