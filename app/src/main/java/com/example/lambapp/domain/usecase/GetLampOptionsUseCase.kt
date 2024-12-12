package com.example.lambapp.domain.usecase

import com.example.lambapp.data.model.Lamp
import com.example.lambapp.domain.repository.LampRepository

interface GetLampOptionsUseCase {

    suspend operator fun invoke() : Lamp
}

class GetLampOptionsUseCaseImpl(
    private val lampRepository: LampRepository
) : GetLampOptionsUseCase {

    override suspend operator fun invoke(): Lamp {
        val state: Boolean = lampRepository.getState()
        val color: String = lampRepository.getCurrentColor()
        val brightness: Int = lampRepository.getCurrentBrightness()
        return Lamp(state = state, color = color, brightness = brightness)
    }
}
