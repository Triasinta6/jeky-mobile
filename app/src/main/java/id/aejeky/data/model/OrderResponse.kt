package id.aejeky.data.model

data class OrderResponse(
    val id: Long?,
    val customerName: String?,
    val phoneNumber: String?,
    val pickupAddress: String?,
    val destinationAddress: String?,
    val status: String?,
    val layanan: Layanan?
)
