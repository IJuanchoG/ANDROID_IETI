package edu.eci.ieti.locaine.ieti.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.eci.ieti.locaine.ieti.datasource.RestDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModel {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://randomuser.me/api/"


    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }


    @Singleton
    @Provides
    fun RestDataSource(retrofit: Retrofit): RestDataSource = retrofit.create(RestDataSource::class.java)


}