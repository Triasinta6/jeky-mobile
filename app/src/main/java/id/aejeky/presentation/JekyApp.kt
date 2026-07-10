package id.aejeky.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import id.aejeky.data.model.Layanan
import id.aejeky.presentation.screen.first.FirstScreen
import id.aejeky.presentation.screen.home.HomeScreen
import id.aejeky.presentation.screen.login.LoginScreen
import id.aejeky.presentation.screen.order.OrderScreen
import id.aejeky.presentation.screen.register.RegisterScreen
import id.aejeky.presentation.screen.forgotpassword.ForgotPasswordScreen

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
                },
                onRegisterClick = {
                    currentScreen = "register"
                },
                onForgotPasswordClick = {
                    currentScreen = "forgot_password"
                }
            )
        }

        "forgot_password" -> {
            ForgotPasswordScreen(
                onBackToLoginClick = {
                    currentScreen = "login"
                }
            )
        }

        "register" -> {
            RegisterScreen(
                onBackToLoginClick = {
                    currentScreen = "login"
                },
                onRegisterClick = {
                    currentScreen = "login"
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
            } ?: run {
                currentScreen = "home"
            }
        }
    }
}