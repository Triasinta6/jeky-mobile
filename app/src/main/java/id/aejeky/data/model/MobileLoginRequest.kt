package id.aejeky.data.model

data class MobileLoginRequest(
    val emailOrPhone: String,
    val password: String
)