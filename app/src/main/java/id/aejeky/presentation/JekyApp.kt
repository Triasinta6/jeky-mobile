package id.aejeky.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import id.aejeky.data.local.SessionManager
import id.aejeky.data.model.Layanan
import id.aejeky.data.model.OrderHistoryResponse
import id.aejeky.presentation.screen.first.FirstScreen
import id.aejeky.presentation.screen.forgotpassword.ForgotPasswordScreen
import id.aejeky.presentation.screen.home.HomeScreen
import id.aejeky.presentation.screen.login.LoginScreen
import id.aejeky.presentation.screen.order.OrderDetailScreen
import id.aejeky.presentation.screen.order.OrderHistoryScreen
import id.aejeky.presentation.screen.order.OrderScreen
import id.aejeky.presentation.screen.profile.ProfileScreen
import id.aejeky.presentation.screen.register.RegisterScreen

@Composable
fun JekyApp() {
    val context = LocalContext.current

    val sessionManager = remember {
        SessionManager(context)
    }

    var currentScreen by remember {
        mutableStateOf(
            if (sessionManager.isLoggedIn()) {
                "home"
            } else {
                "first"
            }
        )
    }

    var selectedService by remember {
        mutableStateOf<Layanan?>(null)
    }

    var selectedOrder by remember {
        mutableStateOf<OrderHistoryResponse?>(null)
    }

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
                },
                onLogoutClick = {
                    sessionManager.clearSession()
                    selectedService = null
                    selectedOrder = null
                    currentScreen = "first"
                },
                onProfileClick = {
                    currentScreen = "profile"
                },
                onOrderHistoryClick = {
                    currentScreen = "order_history"
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

        "order_history" -> {
            OrderHistoryScreen(
                onBackClick = {
                    currentScreen = "home"
                },
                onOrderClick = { order ->
                    selectedOrder = order
                    currentScreen = "order_detail"
                }
            )
        }

        "order_detail" -> {
            selectedOrder?.let { order ->
                OrderDetailScreen(
                    order = order,
                    onBackClick = {
                        currentScreen = "order_history"
                    }
                )
            } ?: run {
                currentScreen = "order_history"
            }
        }

        "profile" -> {
            ProfileScreen(
                onBackClick = {
                    currentScreen = "home"
                },
                onLogoutClick = {
                    sessionManager.clearSession()
                    selectedService = null
                    selectedOrder = null
                    currentScreen = "first"
                }
            )
        }
    }
}