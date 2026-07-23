package id.aejeky.presentation.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun OrderDetailScreen(
    order: OrderHistoryResponse,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 32.dp)
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
            text = "Detail Order",
            color = TextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Informasi lengkap pesanan kamu.",
            color = TextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

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
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = formatRupiah(order.layanan?.hargaDasar ?: 0),
                color = PrimaryBlue,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            StatusBadge(
                status = order.status
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailSection(title = "Informasi Perjalanan") {
            DetailRow(
                label = "Lokasi Jemput",
                value = order.pickupAddress ?: "-"
            )

            DetailRow(
                label = "Tujuan",
                value = order.destinationAddress ?: "-"
            )

            DetailRow(
                label = "Catatan",
                value = order.note ?: "-"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailSection(title = "Informasi Customer") {
            DetailRow(
                label = "Nama",
                value = order.customerName ?: "-"
            )

            DetailRow(
                label = "Nomor HP",
                value = order.phoneNumber ?: "-"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailSection(title = "Informasi Order") {
            DetailRow(
                label = "ID Order",
                value = "#${order.id}"
            )

            DetailRow(
                label = "Tanggal Order",
                value = formatOrderDate(order.createdAt)
            )

            DetailRow(
                label = "Status",
                value = getOrderStatusLabel(order.status)
            )
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .background(White)
            .padding(18.dp)
    ) {
        Text(
            text = title,
            color = TextPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        content()
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = value,
            color = TextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StatusBadge(
    status: String?
) {
    Text(
        text = getOrderStatusLabel(status),
        color = getOrderStatusColor(status),
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(getOrderStatusColor(status).copy(alpha = 0.12f))
            .padding(horizontal = 12.dp, vertical = 7.dp)
    )
}

private fun getOrderStatusLabel(status: String?): String {
    return when (status) {
        "WAITING" -> "Menunggu"
        "ACCEPTED" -> "Diterima"
        "ON_PROGRESS" -> "Diproses"
        "COMPLETED" -> "Selesai"
        "CANCELLED" -> "Dibatalkan"
        else -> status ?: "-"
    }
}

private fun getOrderStatusColor(status: String?): androidx.compose.ui.graphics.Color {
    return when (status) {
        "WAITING" -> PrimaryBlue
        "ACCEPTED" -> SuccessGreen
        "ON_PROGRESS" -> PrimaryBlue
        "COMPLETED" -> SuccessGreen
        "CANCELLED" -> ErrorRed
        else -> TextSecondary
    }
}

private fun formatOrderDate(createdAt: String?): String {
    if (createdAt.isNullOrBlank()) {
        return "-"
    }

    return try {
        val dateTimeParts = createdAt.split("T")
        val dateParts = dateTimeParts[0].split("-")
        val timeParts = dateTimeParts[1].split(":")

        val year = dateParts[0]
        val month = dateParts[1]
        val day = dateParts[2]
        val hour = timeParts[0]
        val minute = timeParts[1]

        "$day/$month/$year, $hour:$minute"
    } catch (e: Exception) {
        createdAt
    }
}