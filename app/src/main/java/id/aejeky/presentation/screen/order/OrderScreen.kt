package id.aejeky.presentation.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.api.ApiClient
import id.aejeky.data.local.SessionManager
import id.aejeky.data.model.Layanan
import id.aejeky.data.model.OrderRequest
import id.aejeky.presentation.theme.BorderGray
import id.aejeky.presentation.theme.ErrorRed
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ScreenBackground
import id.aejeky.presentation.theme.SoftBlueBackground
import id.aejeky.presentation.theme.SuccessGreen
import id.aejeky.presentation.theme.TextPlaceholder
import id.aejeky.presentation.theme.TextPrimary
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White
import id.aejeky.util.formatRupiah
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(
    service: Layanan,
    onBackClick: () -> Unit,
    onOrderSuccess: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = remember {
        SessionManager(context)
    }

    val scope = rememberCoroutineScope()

    var pickupAddress by remember { mutableStateOf("") }
    var destinationAddress by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }
    var isSuccessMessage by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun createOrder() {
        message = ""
        isSuccessMessage = false

        if (sessionManager.getNoHp().isBlank()) {
            message = "Silakan lengkapi nomor HP di profil terlebih dahulu."
            return
        }

        if (pickupAddress.isBlank()) {
            message = "Lokasi jemput wajib diisi"
            return
        }

        if (destinationAddress.isBlank()) {
            message = "Tujuan wajib diisi"
            return
        }

        scope.launch {
            isLoading = true

            try {
                ApiClient.service.createOrder(
                    OrderRequest(
                        customerId = sessionManager.getCustomerId(),
                        layananId = service.id,
                        customerName = sessionManager.getName(),
                        phoneNumber = sessionManager.getNoHp(),
                        pickupAddress = pickupAddress,
                        destinationAddress = destinationAddress,
                        note = note.ifBlank { null }
                    )
                )

                isSuccessMessage = true
                message = "Order berhasil dibuat"
                onOrderSuccess()
            } catch (e: Exception) {
                isSuccessMessage = false
                message = e.message ?: "Gagal membuat order"
            } finally {
                isLoading = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
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
                        .background(White)
                        .clickable {
                            onBackClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Kembali",
                        tint = TextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = service.nama,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            MapPreviewCard()

            Spacer(modifier = Modifier.height(14.dp))

            LocationInputCard(
                pickupAddress = pickupAddress,
                onPickupAddressChange = {
                    pickupAddress = it
                    message = ""
                },
                destinationAddress = destinationAddress,
                onDestinationAddressChange = {
                    destinationAddress = it
                    message = ""
                }
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Pilih Layanan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            SelectedServiceCard(
                service = service
            )

            Spacer(modifier = Modifier.height(22.dp))

            OrderInputField(
                value = note,
                onValueChange = {
                    note = it
                    message = ""
                },
                title = "Catatan",
                placeholder = "Contoh: tunggu di depan gerbang"
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Metode Pembayaran",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentRow(
                title = "JekyPay",
                subtitle = "Pilih metode pembayaran"
            )

            Spacer(modifier = Modifier.height(12.dp))

            PromoRow()

            Spacer(modifier = Modifier.height(26.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (isSuccessMessage) SuccessGreen else ErrorRed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    createOrder()
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Text(
                    text = if (isLoading) "Memproses..." else "Pesan ${service.nama}",
                    color = White,
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
            .background(SoftBlueBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Peta rute perjalanan",
            color = TextSecondary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Lokasi jemput",
            tint = SuccessGreen,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 42.dp, top = 34.dp)
                .size(38.dp)
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Tujuan",
            tint = ErrorRed,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 42.dp, bottom = 34.dp)
                .size(38.dp)
        )
    }
}

@Composable
private fun LocationInputCard(
    pickupAddress: String,
    onPickupAddressChange: (String) -> Unit,
    destinationAddress: String,
    onDestinationAddressChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 7.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false
            )
            .clip(RoundedCornerShape(24.dp))
            .background(White)
            .padding(18.dp)
    ) {
        LocationInputRow(
            title = "Lokasi Jemput",
            value = pickupAddress,
            onValueChange = onPickupAddressChange,
            placeholder = "Masukkan lokasi jemput",
            color = SuccessGreen
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderGray)
        )

        Spacer(modifier = Modifier.height(14.dp))

        LocationInputRow(
            title = "Tujuan",
            value = destinationAddress,
            onValueChange = onDestinationAddressChange,
            placeholder = "Masukkan lokasi tujuan",
            color = ErrorRed
        )
    }
}

@Composable
private fun LocationInputRow(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
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
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = TextPlaceholder,
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = BorderGray,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    cursorColor = PrimaryBlue
                )
            )
        }
    }
}

@Composable
private fun SelectedServiceCard(
    service: Layanan
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
            .background(White)
            .border(
                width = 1.5.dp,
                color = PrimaryBlue,
                shape = RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(PrimaryBlue.copy(alpha = 0.10f)),
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
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Estimasi otomatis",
                fontSize = 13.sp,
                color = TextSecondary
            )
        }

        Text(
            text = formatRupiah(service.hargaDasar ?: 0),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun OrderInputField(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    placeholder: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextPlaceholder,
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = BorderGray,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                cursorColor = PrimaryBlue
            )
        )
    }
}

@Composable
private fun PaymentRow(
    title: String,
    subtitle: String
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
            .background(White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(PrimaryBlue.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountBalanceWallet,
                contentDescription = title,
                tint = PrimaryBlue,
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
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = TextSecondary
            )
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Pilih pembayaran",
            tint = TextPlaceholder,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun PromoRow() {
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
            .background(White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(PrimaryBlue.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "%",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }

        Spacer(modifier = Modifier.size(14.dp))

        Text(
            text = "Gunakan Promo",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Gunakan promo",
            tint = TextPlaceholder,
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