package com.example.emptyexercise.dagger

import com.example.emptyexercise.data.PokeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindsPokeRepo(impl: PokeRepo.Impl): PokeRepo
}