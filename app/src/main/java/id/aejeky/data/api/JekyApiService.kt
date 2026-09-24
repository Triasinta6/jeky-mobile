package id.aejeky.data.api

import id.aejeky.data.model.ResetPasswordRequest
import id.aejeky.data.model.ResetPasswordResponse
import id.aejeky.data.model.ForgotPasswordRequest
import id.aejeky.data.model.ForgotPasswordResponse
import id.aejeky.data.model.Layanan
import id.aejeky.data.model.MobileAuthResponse
import id.aejeky.data.model.MobileLoginRequest
import id.aejeky.data.model.MobileRegisterRequest
import id.aejeky.data.model.OrderHistoryResponse
import id.aejeky.data.model.OrderRequest
import id.aejeky.data.model.OrderResponse
import id.aejeky.data.model.ProfileRequest
import id.aejeky.data.model.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @POST("mobile/auth/login")
    suspend fun loginCustomer(
        @Body request: MobileLoginRequest
    ): Response<MobileAuthResponse>

    @POST("mobile/auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

    @POST("mobile/auth/reset-password")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): Response<ResetPasswordResponse>

    @GET("mobile/profile/{customerId}")
    suspend fun getCustomerProfile(
        @Path("customerId") customerId: Long
    ): ProfileResponse

    @PUT("mobile/profile/{customerId}")
    suspend fun updateCustomerProfile(
        @Path("customerId") customerId: Long,
        @Body request: ProfileRequest
    ): ProfileResponse

    @GET("orders/customer/{customerId}")
    suspend fun getCustomerOrders(
        @Path("customerId") customerId: Long
    ): List<OrderHistoryResponse>
}