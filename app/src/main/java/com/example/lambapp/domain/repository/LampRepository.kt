package com.example.lambapp.domain.repository

import com.example.lambapp.data.model.ColorRequest
import com.example.lambapp.data.model.Lamp
import com.example.lambapp.data.model.LampColorParam

interface LampRepository {

    suspend fun getState() : Boolean

    suspend fun getCurrentColor() : String

    suspend fun getCurrentBrightness() : Int

    suspend fun getAllColors() : List<LampColorParam>

    suspend fun updateLampState(action: String) : Boolean

    suspend fun changeColor(color: String): Boolean

    //suspend fun updateLampBrightness(lamp: Lamp, brightness: Int)
}
