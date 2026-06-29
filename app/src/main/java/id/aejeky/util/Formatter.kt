package id.aejeky.util

fun formatRupiah(value: Int): String {
    return "Rp " + "%,d".format(value).replace(",", ".")
}
