package edu.eci.ieti.locaine.ieti.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.eci.ieti.locaine.ieti.datasource.RestDataSource
import edu.eci.ieti.locaine.ieti.repository.UserRepository
import edu.eci.ieti.locaine.ieti.repository.UserRepositoryImp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Provides
    abstract fun userRepository(repo:UserRepositoryImp) : UserRepository



}