package id.aejeky.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import id.aejeky.data.api.ApiClient
import id.aejeky.data.model.Layanan
import id.aejeky.presentation.component.ServiceCard

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
