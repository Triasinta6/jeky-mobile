package id.aejeky.data.model

data class Layanan(
    val id: Long,
    val nama: String,
    val deskripsi: String?,
    val hargaDasar: Int?,
    val aktif: Boolean?
)
