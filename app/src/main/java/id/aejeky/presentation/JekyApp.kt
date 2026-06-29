package id.aejeky.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.aejeky.data.model.Layanan
import id.aejeky.presentation.screen.HomeScreen
import id.aejeky.presentation.screen.OrderScreen

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
