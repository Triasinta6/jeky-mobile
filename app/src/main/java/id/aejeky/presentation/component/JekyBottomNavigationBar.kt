package id.aejeky.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.TextPlaceholder
import id.aejeky.presentation.theme.White

data class BottomMenuItem(
    val key: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun JekyBottomNavigationBar(
    selectedMenu: String,
    onMenuClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
            .background(White)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        menus.forEach { menu ->
            val isSelected = selectedMenu == menu.key
            val itemColor = if (isSelected) PrimaryBlue else TextPlaceholder

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