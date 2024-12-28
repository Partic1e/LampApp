package com.example.lambapp.di

import android.app.Application
import android.content.Context
import com.example.lambapp.data.network.LampService
import com.example.lambapp.data.repository.LampRepositoryImpl
import com.example.lambapp.domain.repository.LampRepository
import com.example.lambapp.domain.usecase.ChangeBrightnessLampUseCase
import com.example.lambapp.domain.usecase.ChangeBrightnessLampUseCaseImpl
import com.example.lambapp.domain.usecase.ChangeColorLampUseCase
import com.example.lambapp.domain.usecase.ChangeColorLampUseCaseImpl
import com.example.lambapp.domain.usecase.ChangeStateLampUseCase
import com.example.lambapp.domain.usecase.ChangeStateLampUseCaseImpl
import com.example.lambapp.domain.usecase.GetLampOptionsUseCase
import com.example.lambapp.domain.usecase.GetLampOptionsUseCaseImpl
import com.example.lambapp.domain.usecase.GetListOfColorsUseCase
import com.example.lambapp.domain.usecase.GetListOfColorsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface AppBindsModule {

    @Binds
    @Singleton
    fun bindGetLampOptionsUseCase(useCase: GetLampOptionsUseCaseImpl): GetLampOptionsUseCase

    @Binds
    @Singleton
    fun bindGetListOfColorsUseCase(useCase: GetListOfColorsUseCaseImpl): GetListOfColorsUseCase

    @Binds
    @Singleton
    fun bindChangeStateLampUseCase(useCase: ChangeStateLampUseCaseImpl): ChangeStateLampUseCase

    @Binds
    @Singleton
    fun bindChangeColorLampUseCase(useCase: ChangeColorLampUseCaseImpl): ChangeColorLampUseCase

    @Binds
    @Singleton
    fun bindChangeBrightnessLampUseCase(useCase: ChangeBrightnessLampUseCaseImpl): ChangeBrightnessLampUseCase

    @Binds
    @Singleton
    fun bindLampRepository(repository: LampRepositoryImpl): LampRepository

    companion object {

        @Provides
        fun provideContext(app: Application): Context = app.applicationContext

        @Provides
        fun provideLampService(): LampService =
            Retrofit.Builder()
                .baseUrl("http://46.17.45.34:1337/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LampService::class.java)
    }
}
