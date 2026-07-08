package id.aejeky.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.aejeky.data.model.Layanan
import id.aejeky.presentation.theme.PrimaryBlue
import id.aejeky.presentation.theme.ServiceFoodOrange
import id.aejeky.presentation.theme.ServiceMartPurple
import id.aejeky.presentation.theme.ServiceSendGreen
import id.aejeky.presentation.theme.TextSecondary
import id.aejeky.presentation.theme.White

@Composable
fun JekyServiceCard(
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
            .background(White)
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
                color = TextSecondary,
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
        name.contains("ride") -> PrimaryBlue
        name.contains("food") -> ServiceFoodOrange
        name.contains("send") || name.contains("delivery") -> ServiceSendGreen
        name.contains("mart") -> ServiceMartPurple
        else -> PrimaryBlue
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