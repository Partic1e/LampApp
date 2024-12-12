package com.example.lambapp.domain.repository

import com.example.lambapp.data.model.Lamp

interface LampRepository {

    suspend fun getState() : Boolean

    suspend fun getCurrentColor() : String

    suspend fun getCurrentBrightness() : Int

    suspend fun updateLampState(lamp: Lamp, action: String) : Boolean

    //suspend fun updateLampColor(lamp: Lamp, color: String)

    //suspend fun updateLampBrightness(lamp: Lamp, brightness: Int)
}
