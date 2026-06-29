package id.aejeky.data.api

import id.aejeky.data.model.Layanan
import id.aejeky.data.model.OrderRequest
import id.aejeky.data.model.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface JekyApiService {
    @GET("layanan/aktif")
    suspend fun getLayananAktif(): List<Layanan>

    @POST("orders")
    suspend fun createOrder(@Body request: OrderRequest): OrderResponse
}
