package id.aejeky.data.model

class ProfileResponse (
    val success: Boolean,
    val message: String,
    val customerId: Long?,
    val name: String?,
    val email: String?,
    val noHp: String?,
    val address: String?
)