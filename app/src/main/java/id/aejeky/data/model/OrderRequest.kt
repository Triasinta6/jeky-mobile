package id.aejeky.data.model

data class OrderRequest(
    val layananId: Long,
    val customerName: String,
    val phoneNumber: String,
    val pickupAddress: String,
    val destinationAddress: String
)
