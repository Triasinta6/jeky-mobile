package id.aejeky.data.api

import id.aejeky.data.model.Layanan
import id.aejeky.data.model.OrderRequest
import id.aejeky.data.model.OrderResponse
import id.aejeky.data.model.MobileRegisterRequest
import id.aejeky.data.model.MobileAuthResponse
import id.aejeky.data.model.ForgotPasswordRequest
import id.aejeky.data.model.ForgotPasswordResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response

interface JekyApiService {
    @GET("layanan/aktif")
    suspend fun getLayananAktif(): List<Layanan>

    @POST("orders")
    suspend fun createOrder(
        @Body request: OrderRequest
    ): OrderResponse

    @POST("mobile/auth/register")
    suspend fun registerCustomer(
        @Body request: MobileRegisterRequest
    ): Response<MobileAuthResponse>

    @POST("mobile/auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>
}
