package edu.eci.ieti.locaine.ieti.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.eci.ieti.locaine.ieti.repository.UserRepository
import edu.eci.ieti.locaine.ieti.repository.UserRepositoryImp
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun userRepository(repo:UserRepositoryImp) : UserRepository



}