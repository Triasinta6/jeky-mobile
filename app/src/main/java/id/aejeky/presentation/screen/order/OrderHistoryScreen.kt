package id.aejeky.presentation.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.api.ApiClient
import id.aejeky.data.local.SessionManager
import id.aejeky.data.model.OrderHistoryResponse
import id.aejeky.presentation.theme.ErrorRed
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ScreenBackground
import id.aejeky.presentation.theme.SuccessGreen
import id.aejeky.presentation.theme.TextPrimary
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White
import id.aejeky.util.formatRupiah

@Composable
fun OrderHistoryScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = remember {
        SessionManager(context)
    }

    var orders by remember {
        mutableStateOf<List<OrderHistoryResponse>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        try {
            orders = ApiClient.service.getCustomerOrders(
                customerId = sessionManager.getCustomerId()
            )
        } catch (e: Exception) {
            errorMessage = e.message ?: "Gagal memuat riwayat order"
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = White
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                tint = TextPrimary
            )

            Text(
                text = "Kembali",
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Riwayat Order",
            color = TextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Daftar pesanan yang pernah kamu buat.",
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        when {
            isLoading -> {
                Text(
                    text = "Memuat riwayat order...",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }

            errorMessage.isNotEmpty() -> {
                Text(
                    text = errorMessage,
                    color = ErrorRed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            orders.isEmpty() -> {
                Text(
                    text = "Belum ada riwayat order.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }

            else -> {
                orders.forEach { order ->
                    OrderHistoryCard(order = order)

                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
private fun OrderHistoryCard(
    order: OrderHistoryResponse
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(22.dp),
                clip = false
            )
            .clip(RoundedCornerShape(22.dp))
            .background(White)
            .padding(18.dp)
    ) {
        Text(
            text = order.layanan?.nama ?: "Layanan Jeky",
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = formatRupiah(order.layanan?.hargaDasar ?: 0),
            color = PrimaryBlue,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Dari: ${order.pickupAddress ?: "-"}",
            color = TextPrimary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Ke: ${order.destinationAddress ?: "-"}",
            color = TextPrimary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Status: ${order.status ?: "-"}",
            color = if (order.status == "WAITING") SuccessGreen else TextSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = order.createdAt ?: "",
            color = TextSecondary,
            fontSize = 12.sp
        )
    }
}