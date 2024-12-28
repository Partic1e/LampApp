package com.example.lambapp.domain.usecase

import com.example.lambapp.data.model.Lamp
import com.example.lambapp.data.model.LampState
import com.example.lambapp.domain.repository.LampRepository
import javax.inject.Inject

interface GetLampOptionsUseCase {

    suspend operator fun invoke() : Result<Lamp?>
}

class GetLampOptionsUseCaseImpl @Inject constructor(
    private val lampRepository: LampRepository
) : GetLampOptionsUseCase {

    override suspend operator fun invoke(): Result<Lamp?> {

        val stateResult = lampRepository.getState()

        val errors = mutableListOf<Throwable>()

        val state = stateResult.getOrElse {
            errors.add(it)
            null
        }?.let { isOn ->
            if (isOn) LampState.ON else LampState.OFF
        }
        if (state == LampState.OFF) {
            return if (errors.isEmpty()) {
                Result.success(Lamp(state = LampState.OFF, color = "empty", brightness = 0))
            } else {
                Result.failure(Exception("$errors"))
            }
        }

        val colorResult = lampRepository.getCurrentColor()
        val brightnessResult = lampRepository.getCurrentBrightness()

        val color = colorResult.getOrElse {
            errors.add(it)
            null
        }
        val brightness = brightnessResult.getOrElse {
            errors.add(it)
            null
        }

        return if (errors.isEmpty()) {
            Result.success(Lamp(state = state!!, color = color!!, brightness = brightness!!))
        } else {
            Result.failure(Exception("$errors"))
        }
    }
}
