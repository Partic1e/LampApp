package com.example.lambapp.domain.usecase

import com.example.lambapp.data.model.Lamp
import com.example.lambapp.domain.repository.LampRepository
import javax.inject.Inject

interface ChangeStateLampUseCase {

    suspend operator fun invoke(lamp: Lamp): Result<Boolean?>
}

class ChangeStateLampUseCaseImpl @Inject constructor(
    private val lampRepository: LampRepository
) : ChangeStateLampUseCase {

    override suspend operator fun invoke(lamp: Lamp): Result<Boolean?> =
        lampRepository.changeState(lamp)
}
