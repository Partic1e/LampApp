package com.example.lambapp.data.repository

import android.util.Log
import com.example.lambapp.data.model.Lamp
import com.example.lambapp.data.network.LampService
import com.example.lambapp.domain.repository.LampRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.EOFException
import javax.inject.Inject

class LampRepositoryImpl @Inject constructor(
    private val lampService: LampService
) : LampRepository {

    override suspend fun getState(): Result<Boolean?> {
        kotlin.runCatching {
            lampService.getState()
        }.fold(
            onSuccess = {
                if (!it.isSuccessful && it.code() == 400) {
                    return Result.success(it.body())
                }
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun getCurrentColor(): Result<String?> {
        kotlin.runCatching {
            lampService.getCurrentColor()
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body()?.color)
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun getCurrentBrightness(): Result<Int?> {
        kotlin.runCatching {
            lampService.getCurrentBrightness()
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun getAllColors(): Result<List<String>?> {
        kotlin.runCatching {
            lampService.getAllColors()
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body()?.map { colorParam ->
                        colorParam.color
                    })
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun changeState(lamp: Lamp): Result<Boolean?> {
        kotlin.runCatching {
            lampService.changeState(lamp.state.action)
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun changeColor(lamp: Lamp): Result<Boolean?> {
        kotlin.runCatching {
            lampService.changeColor(lamp.color)
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }

    override suspend fun changeBrightness(lamp: Lamp): Result<Boolean?> {
        kotlin.runCatching {
            lampService.changeBrightness(lamp.brightness)
        }.fold(
            onSuccess = {
                return if (it.isSuccessful)
                    Result.success(it.body())
                else Result.failure(HttpException(it))
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }
}
