package com.example.lambapp.domain.usecase

import com.example.lambapp.data.model.Lamp
import com.example.lambapp.domain.repository.LampRepository
import javax.inject.Inject

interface ChangeColorLampUseCase {

    suspend operator fun invoke(lamp: Lamp): Result<Boolean?>
}

class ChangeColorLampUseCaseImpl @Inject constructor(
    private val lampRepository: LampRepository
) : ChangeColorLampUseCase {

    override suspend operator fun invoke(lamp: Lamp): Result<Boolean?> =
        lampRepository.changeColor(lamp)
}
