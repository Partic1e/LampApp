package com.example.lambapp.data.repository

import com.example.lambapp.data.model.Lamp
import com.example.lambapp.data.model.LampColorParam
import com.example.lambapp.data.network.LampService
import com.example.lambapp.domain.repository.LampRepository

class LampRepositoryImpl(private val api: LampService) : LampRepository {

    override suspend fun getState(): Boolean {
        val response = api.getState()
        return response.body()!!
    }

    override suspend fun getCurrentColor(): String {
        val response = api.getCurrentColor()
        return response.body()!!.color
    }

    override suspend fun getCurrentBrightness(): Int {
        val response = api.getCurrentBrightness()
        return response.body()!!
    }

    override suspend fun getAllColors(): List<LampColorParam> {
        val response = api.getAllColors()
        return response.body()!!
    }

    override suspend fun updateLampState(lamp: Lamp, action: String): Boolean {
        val response = api.updateLampState(action)
        return response.isSuccessful
    }
}
