package id.aejeky.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "http://172.16.171.5:8080/api/"

data class Layanan(
    val id: Long,
    val nama: String,
    val deskripsi: String?,
    val hargaDasar: Int?,
    val aktif: Boolean?
)

data class OrderRequest(
    val layananId: Long,
    val customerName: String,
    val phoneNumber: String,
    val pickupAddress: String,
    val destinationAddress: String
)

data class OrderResponse(
    val id: Long?,
    val customerName: String?,
    val phoneNumber: String?,
    val pickupAddress: String?,
    val destinationAddress: String?,
    val status: String?,
    val layanan: Layanan?
)

interface JekyApiService {
    @GET("layanan/aktif")
    suspend fun getLayananAktif(): List<Layanan>

    @POST("orders")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse
}

object ApiClient {
    val service: JekyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JekyApiService::class.java)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    JekyApp()
                }
            }
        }
    }
}

@Composable
fun JekyApp() {
    var selectedService by remember { mutableStateOf<Layanan?>(null) }

    if (selectedService == null) {
        HomeScreen(
            onServiceClick = { service ->
                selectedService = service
            }
        )
    } else {
        OrderScreen(
            service = selectedService!!,
            onBackClick = {
                selectedService = null
            }
        )
    }
}

@Composable
fun HomeScreen(
    onServiceClick: (Layanan) -> Unit
) {
    var services by remember { mutableStateOf<List<Layanan>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            services = ApiClient.service.getLayananAktif()
            errorMessage = ""
        } catch (e: Exception) {
            errorMessage = "Gagal ambil layanan. Pastikan backend aktif dan IP benar."
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_jeky),
                contentDescription = "Jeky Logo",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Hello, Jeky User!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Mau ke mana hari ini?",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF8BC34A)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Saldo JekyPay",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Rp 125.000",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Layanan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading -> {
                Text(
                    text = "Memuat layanan...",
                    color = Color.Gray
                )
            }

            errorMessage.isNotEmpty() -> {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            services.isEmpty() -> {
                Text(
                    text = "Belum ada layanan aktif.",
                    color = Color.Gray
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(services) { service ->
                        ServiceCard(
                            service = service,
                            onClick = {
                                onServiceClick(service)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCard(
    service: Layanan,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(130.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = service.nama,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatRupiah(service.hargaDasar ?: 0),
                fontSize = 13.sp,
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

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

fun formatRupiah(value: Int): String {
    return "Rp " + "%,d".format(value).replace(",", ".")
}