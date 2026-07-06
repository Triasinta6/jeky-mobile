package id.aejeky.presentation.screen

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.R
import id.aejeky.data.api.ApiClient
import id.aejeky.data.model.Layanan
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.filled.Search

@Composable
fun HomeScreen(
    onServiceClick: (Layanan) -> Unit
) {
    var services by remember { mutableStateOf<List<Layanan>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var selectedBottomMenu by remember { mutableStateOf("home") }

    val primaryBlue = Color(0xFF2563EB)
    val darkText = Color(0xFF101828)

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        //box header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .clip(RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0B63F6),
                            Color(0xFF2563EB),
                            Color(0xFF60A5FA)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .padding(bottom = 96.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_jeky),
                    contentDescription = "Profile User",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(primaryBlue)
                )

                //notifikasi
                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Halo,",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Text(
                        text = "Nama User",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = CircleShape,
                            clip = false
                        )
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            // nanti diarahkan ke halaman notifikasi
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifikasi",
                        tint = darkText,
                        modifier = Modifier.size(24.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-9).dp, y = 9.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                }
            }

            //saldo card
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 124.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(22.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 22.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(primaryBlue.copy(alpha = 0.10f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription ="Saldo Jeky",
                            tint = primaryBlue,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Saldo Jeky",
                            color = Color(0xFF667085),
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Rp 50.000",
                                color = Color(0xFF101828),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Detail saldo",
                                tint = primaryBlue,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(primaryBlue.copy(alpha = 0.10f))
                            .clickable {
                                // nanti diarahkan ke halaman top up
                            }
                            .padding(horizontal = 16.dp, vertical = 11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Up",
                            color = primaryBlue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(primaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Top Up",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            //search bar
            Spacer(modifier = Modifier.height(16.dp))

            JekySearchBar(
                modifier = Modifier.fillMaxWidth()
            )

            // Layanan jeky
            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Layanan Jeky",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkText
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
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
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            items(services) { service ->
                                JekyServiceCard(
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

        JekyBottomNavigationBar(
            selectedMenu = selectedBottomMenu,
            onMenuClick = { menu ->
                selectedBottomMenu = menu
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun JekyServiceCard(
    service: Layanan,
    onClick: () -> Unit
) {
    val serviceName = service.nama
    val serviceDescription = service.deskripsi.orEmpty().trim()

    val titleColor = getServiceColor(serviceName)
    val serviceIcon = getServiceIcon(serviceName)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(22.dp),
                clip = false
            )
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(titleColor.copy(alpha = 0.10f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = serviceIcon,
                fontSize = 34.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = serviceName,
                color = titleColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = serviceDescription.ifBlank { "Layanan Jeky" },
                color = Color(0xFF667085),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun getServiceColor(serviceName: String): Color {
    val name = serviceName.lowercase()

    return when {
        name.contains("ride") -> Color(0xFF2563EB)
        name.contains("food") -> Color(0xFFF97316)
        name.contains("send") || name.contains("delivery") -> Color(0xFF039855)
        name.contains("mart") -> Color(0xFF7C3AED)
        else -> Color(0xFF2563EB)
    }
}

private fun getServiceIcon(serviceName: String): String {
    val name = serviceName.lowercase()

    return when {
        name.contains("ride") -> "🛵"
        name.contains("food") -> "🍔"
        name.contains("send") || name.contains("delivery") -> "📦"
        name.contains("mart") -> "🛒"
        else -> "✨"
    }
}

data class BottomMenuItem(
    val key: String,
    val title: String,
    val icon: ImageVector
)

@Composable
private fun JekySearchBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(58.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable {
                // nanti bisa diarahkan ke halaman search / pilih layanan
            }
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Cari layanan",
            tint = Color(0xFF344054),
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Mau pergi atau butuh layanan apa?",
            color = Color(0xFF98A2B3),
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun JekyBottomNavigationBar(
    selectedMenu: String,
    onMenuClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryBlue = Color(0xFF2563EB)
    val inactiveColor = Color(0xFF98A2B3)

    val menus = listOf(
        BottomMenuItem(
            key = "home",
            title = "Home",
            icon = Icons.Default.Home
        ),
        BottomMenuItem(
            key = "order",
            title = "Order",
            icon = Icons.Default.ReceiptLong
        ),
        BottomMenuItem(
            key = "wallet",
            title = "Wallet",
            icon = Icons.Default.AccountBalanceWallet
        ),
        BottomMenuItem(
            key = "profile",
            title = "Profile",
            icon = Icons.Default.Person
        )
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(76.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(28.dp),
                clip = false
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        menus.forEach { menu ->
            val isSelected = selectedMenu == menu.key
            val itemColor = if (isSelected) primaryBlue else inactiveColor

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .clickable {
                        onMenuClick(menu.key)
                    }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = menu.icon,
                    contentDescription = menu.title,
                    tint = itemColor,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = menu.title,
                    color = itemColor,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}