package com.example.lambapp.domain.usecase

import com.example.lambapp.domain.repository.LampRepository

interface GetListOfColorsUseCase {

    suspend operator fun invoke(): List<String>
}

class GetListOfColorsUseCaseImpl(
    private val lampRepository: LampRepository
) : GetListOfColorsUseCase {

    override suspend fun invoke(): List<String> {
        val colorParamsList = lampRepository.getAllColors()
        return colorParamsList.map {
            it.color
        }
    }
}
