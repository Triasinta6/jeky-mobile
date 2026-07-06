package id.aejeky.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FirstScreen(
    onStartClick: () -> Unit
) {
    val primaryBlue = Color(0xFF2563EB)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B63F6),
                        Color(0xFF2563EB),
                        Color(0xFF60A5FA)
                    )
                )
            )
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 38.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Jeky",
                color = Color.White,
                fontSize = 78.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Layanan on-demand\nyang selalu ada untukmu.",
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 22.sp,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(54.dp))

            Box(
                modifier = Modifier
                    .size(210.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DeliveryDining,
                    contentDescription = "Jeky Driver",
                    tint = Color.White,
                    modifier = Modifier.size(120.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RowIndicator()

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "Mulai Sekarang",
                    color = primaryBlue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun RowIndicator() {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 36.dp, height = 5.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White)
        )

        Box(
            modifier = Modifier
                .size(width = 28.dp, height = 5.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.35f))
        )

        Box(
            modifier = Modifier
                .size(width = 28.dp, height = 5.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.35f))
        )
    }
}