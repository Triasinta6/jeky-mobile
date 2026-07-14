package id.aejeky.data.model

data class MobileRegisterRequest(
    val name: String,
    val emailOrPhone: String,
    val password: String,
    val confirmPassword: String
)