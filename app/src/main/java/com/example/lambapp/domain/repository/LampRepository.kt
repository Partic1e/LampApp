package com.example.lambapp.domain.repository

import com.example.lambapp.data.model.Lamp

interface LampRepository {

    suspend fun getState(): Result<Boolean?>

    suspend fun getCurrentColor(): Result<String?>

    suspend fun getCurrentBrightness(): Result<Int?>

    suspend fun getAllColors(): Result<List<String>?>

    suspend fun changeState(lamp: Lamp): Result<Boolean?>

    suspend fun changeColor(lamp: Lamp): Result<Boolean?>

    suspend fun changeBrightness(lamp: Lamp): Result<Boolean?>
}
