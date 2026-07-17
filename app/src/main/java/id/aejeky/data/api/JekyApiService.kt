package id.aejeky.data.api

import id.aejeky.data.model.MobileAuthResponse
import id.aejeky.data.model.MobileLoginRequest
import id.aejeky.data.model.MobileRegisterRequest
import id.aejeky.data.model.Layanan
import id.aejeky.data.model.OrderRequest
import id.aejeky.data.model.OrderResponse
import id.aejeky.data.model.ProfileResponse
import id.aejeky.data.model.ProfileRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response

interface JekyApiService {
    @GET("layanan/aktif")
    suspend fun getLayananAktif(): List<Layanan>

    @POST("orders")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse

    @POST("mobile/auth/register")
    suspend fun registerCustomer(
        @Body request: MobileRegisterRequest
    ): Response<MobileAuthResponse>

    @POST("mobile/auth/login")
    suspend fun loginCustomer(
        @Body request: MobileLoginRequest
    ): Response<MobileAuthResponse>

    @GET("mobile/profile/{customerId}")
    suspend fun getCustomerProfile(
        @Path("customerId") customerId: Long
    ): ProfileResponse

    @PUT("mobile/profile/{customerId}")
    suspend fun updateCustomerProfile(
        @Path("customerId") customerId: Long,
        @Body request: ProfileRequest
    ): ProfileResponse
}
