package com.example.lambapp.data.network

import com.example.lambapp.data.model.LampColorParam
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LampService {

    @GET("state/")
    suspend fun getState(): Response<Boolean>

    @GET("color/current")
    suspend fun getCurrentColor(): Response<LampColorParam>

    @GET("brightness/current")
    suspend fun getCurrentBrightness(): Response<Int>

    @GET("color/")
    suspend fun getAllColors(): Response<List<LampColorParam>>

    @POST("state/{action}")
    suspend fun changeState(@Path("action") action: String): Response<Boolean>

    @POST("color/")
    suspend fun changeColor(@Query("color") color: String): Response<Boolean>

    @POST("brightness/")
    suspend fun changeBrightness(@Query("level") brightness: Int): Response<Boolean>
}