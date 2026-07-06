package id.aejeky.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.model.Layanan
import id.aejeky.util.formatRupiah

@Composable
fun OrderScreen(
    service: Layanan,
    onBackClick: () -> Unit
) {
    val primaryBlue = Color(0xFF2563EB)
    val darkText = Color(0xFF101828)
    val softText = Color(0xFF667085)
    val background = Color(0xFFF7F7F7)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            onBackClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = darkText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = service.nama,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkText
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            MapPreviewCard()

            Spacer(modifier = Modifier.height(14.dp))

            LocationSummaryCard()

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Pilih Layanan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            SelectedServiceCard(
                service = service,
                primaryBlue = primaryBlue
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Metode Pembayaran",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentRow(
                title = "JekyPay",
                subtitle = "Pilih metode pembayaran",
                primaryBlue = primaryBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            PromoRow(
                primaryBlue = primaryBlue
            )

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    // Tampilan dulu.
                    // Nanti setelah login + profile selesai, baru sambungkan ke create order.
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue
                )
            ) {
                Text(
                    text = "Pesan ${service.nama}",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MapPreviewCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(178.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false
            )
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFEFF6FF)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Peta rute perjalanan",
            color = Color(0xFF667085),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Lokasi jemput",
            tint = Color(0xFF12B76A),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 42.dp, top = 34.dp)
                .size(38.dp)
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Tujuan",
            tint = Color(0xFFF04438),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 42.dp, bottom = 34.dp)
                .size(38.dp)
        )
    }
}

@Composable
private fun LocationSummaryCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 7.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false
            )
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(18.dp)
    ) {
        LocationRow(
            title = "Lokasi Jemput",
            value = "Pilih lokasi jemput",
            color = Color(0xFF12B76A)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFE4E7EC))
        )

        Spacer(modifier = Modifier.height(14.dp))

        LocationRow(
            title = "Tujuan",
            value = "Pilih lokasi tujuan",
            color = Color(0xFFF04438)
        )
    }
}

@Composable
private fun LocationRow(
    title: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF667085)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF101828)
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Pilih $title",
            tint = Color(0xFF98A2B3),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SelectedServiceCard(
    service: Layanan,
    primaryBlue: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(22.dp),
                clip = false
            )
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .border(
                width = 1.5.dp,
                color = primaryBlue,
                shape = RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(primaryBlue.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getOrderServiceIcon(service.nama),
                fontSize = 34.sp
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = service.nama,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF101828)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Estimasi otomatis",
                fontSize = 13.sp,
                color = Color(0xFF667085)
            )
        }

        Text(
            text = formatRupiah(service.hargaDasar ?: 0),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = primaryBlue,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun PaymentRow(
    title: String,
    subtitle: String,
    primaryBlue: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(primaryBlue.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = title,
                tint = primaryBlue,
                modifier = Modifier.size(25.dp)
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF101828)
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color(0xFF667085)
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Pilih pembayaran",
            tint = Color(0xFF98A2B3),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun PromoRow(
    primaryBlue: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(primaryBlue.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = primaryBlue
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Text(
            text = "Gunakan Promo",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF101828)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Gunakan promo",
            tint = Color(0xFF98A2B3),
            modifier = Modifier.size(24.dp)
        )
    }
}

private fun getOrderServiceIcon(serviceName: String): String {
    val name = serviceName.lowercase()

    return when {
        name.contains("ride") -> "🛵"
        name.contains("food") -> "🍔"
        name.contains("send") || name.contains("delivery") -> "📦"
        name.contains("mart") -> "🛒"
        else -> "✨"
    }
}