package id.aejeky.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.aejeky.data.model.Layanan
import id.aejeky.presentation.screen.FirstScreen
import id.aejeky.presentation.screen.HomeScreen
import id.aejeky.presentation.screen.LoginScreen
import id.aejeky.presentation.screen.OrderScreen

@Composable
fun JekyApp() {
    var currentScreen by remember { mutableStateOf("first") }
    var selectedService by remember { mutableStateOf<Layanan?>(null) }

    when (currentScreen) {
        "first" -> {
            FirstScreen(
                onStartClick = {
                    currentScreen = "login"
                }
            )
        }

        "login" -> {
            LoginScreen(
                onBackClick = {
                    currentScreen = "first"
                },
                onLoginClick = {
                    currentScreen = "home"
                }
            )
        }

        "home" -> {
            HomeScreen(
                onServiceClick = { service ->
                    selectedService = service
                    currentScreen = "order"
                }
            )
        }

        "order" -> {
            selectedService?.let { service ->
                OrderScreen(
                    service = service,
                    onBackClick = {
                        selectedService = null
                        currentScreen = "home"
                    }
                )
            }
        }
    }
}