package com.example.lambapp.domain.usecase

import com.example.lambapp.domain.repository.LampRepository

interface ChangeColorLampUseCase {

    suspend operator fun invoke(color: String): Boolean
}

class ChangeColorLampUseCaseImpl(
    private val lampRepository: LampRepository
) : ChangeColorLampUseCase {

    override suspend operator fun invoke(color: String): Boolean {
        val result = lampRepository.changeColor(color)
        return result
    }
}
