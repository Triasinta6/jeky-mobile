package id.aejeky.data.model

data class OrderHistoryResponse(
    val id: Long,
    val customerName: String?,
    val phoneNumber: String?,
    val pickupAddress: String?,
    val destinationAddress: String?,
    val note: String?,
    val status: String?,
    val createdAt: String?,
    val layanan: OrderHistoryLayanan?
)

data class OrderHistoryLayanan(
    val id: Long?,
    val nama: String?,
    val deskripsi: String?,
    val hargaDasar: Int?,
    val aktif: Boolean?
)