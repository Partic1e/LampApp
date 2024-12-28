package com.example.lambapp.di

import com.example.lambapp.di.viewModel.ViewModelModule
import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        AppBindsModule::class
    ]
)
class AppModule
