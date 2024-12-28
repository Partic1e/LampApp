package com.example.lambapp.domain.usecase

import com.example.lambapp.domain.repository.LampRepository
import javax.inject.Inject

interface GetListOfColorsUseCase {

    suspend operator fun invoke(): Result<List<String>?>
}

class GetListOfColorsUseCaseImpl @Inject constructor(
    private val lampRepository: LampRepository
) : GetListOfColorsUseCase {

    override suspend fun invoke(): Result<List<String>?> =
        lampRepository.getAllColors()
}