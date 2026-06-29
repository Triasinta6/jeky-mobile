package id.aejeky.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.api.ApiClient
import id.aejeky.data.model.Layanan
import id.aejeky.data.model.OrderRequest
import id.aejeky.util.formatRupiah
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(
    service: Layanan,
    onBackClick: () -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var pickup by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(20.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text(text = "< Kembali")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = service.nama,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = service.deskripsi ?: "Isi detail pesanan kamu",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = customerName,
            onValueChange = {
                customerName = it
                successMessage = ""
                errorMessage = ""
            },
            label = { Text("Nama Customer") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                successMessage = ""
                errorMessage = ""
            },
            label = { Text("Nomor HP") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = pickup,
            onValueChange = {
                pickup = it
                successMessage = ""
                errorMessage = ""
            },
            label = { Text("Lokasi Jemput") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = destination,
            onValueChange = {
                destination = it
                successMessage = ""
                errorMessage = ""
            },
            label = { Text("Tujuan") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (
                    customerName.isBlank() ||
                    phoneNumber.isBlank() ||
                    pickup.isBlank() ||
                    destination.isBlank()
                ) {
                    errorMessage = "Semua field wajib diisi."
                    successMessage = ""
                    return@Button
                }

                coroutineScope.launch {
                    try {
                        isLoading = true
                        errorMessage = ""
                        successMessage = ""

                        val request = OrderRequest(
                            layananId = service.id,
                            customerName = customerName,
                            phoneNumber = phoneNumber,
                            pickupAddress = pickup,
                            destinationAddress = destination
                        )

                        val response = ApiClient.service.createOrder(request)

                        successMessage = "Order berhasil dibuat. ID Order: ${response.id ?: "-"}"

                        customerName = ""
                        phoneNumber = ""
                        pickup = ""
                        destination = ""
                    } catch (e: Exception) {
                        errorMessage = "Gagal membuat order. Cek backend atau koneksi."
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8BC34A)
            )
        ) {
            Text(text = if (isLoading) "Mengirim..." else "Pesan Sekarang")
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        if (successMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = "Berhasil",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = successMessage,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Ringkasan Layanan",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Layanan: ${service.nama}")
                Text(text = "Estimasi Harga: ${formatRupiah(service.hargaDasar ?: 0)}")
            }
        }
    }
}
