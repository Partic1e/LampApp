package com.example.lambapp.data.network

import com.example.lambapp.data.model.LampColorParam
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LampService {

    @GET("state/")
    suspend fun getState(): Response<Boolean>

    @GET("color/current")
    suspend fun getCurrentColor(): Response<LampColorParam>

    @GET("brightness/current")
    suspend fun getCurrentBrightness(): Response<Int>

    @POST("state/{action}")
    suspend fun updateLampState(@Path("action") action: String): Response<Void>
}