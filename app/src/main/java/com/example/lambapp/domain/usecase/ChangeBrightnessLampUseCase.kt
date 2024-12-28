package com.example.lambapp.domain.usecase

import com.example.lambapp.data.model.Lamp
import com.example.lambapp.domain.repository.LampRepository
import javax.inject.Inject

interface ChangeBrightnessLampUseCase {

    suspend operator fun invoke(lamp: Lamp): Result<Boolean?>
}

class ChangeBrightnessLampUseCaseImpl @Inject constructor(
    private val lampRepository: LampRepository
) : ChangeBrightnessLampUseCase {

    override suspend operator fun invoke(lamp: Lamp): Result<Boolean?> {
        return lampRepository.changeBrightness(lamp)
    }
}
