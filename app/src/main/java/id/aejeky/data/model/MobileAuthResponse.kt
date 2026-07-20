package id.aejeky.data.model

data class MobileAuthResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val customerId: Long?,
    val name: String?,
    val email: String?,
    val noHp: String?
)