package edu.eci.ieti.locaine.ieti.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.eci.ieti.locaine.ieti.datasource.DBDataSource
import edu.eci.ieti.locaine.ieti.datasource.RestDataSource
import edu.eci.ieti.locaine.ieti.datasource.RestLocaineDataSource
import edu.eci.ieti.locaine.ieti.model.UserDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import android.content.Intent
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import okhttp3.*
import java.io.IOException


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://randomuser.me/api/"

    @Singleton
    @Provides
    @Named("LocaineUrl")
    fun provideLocaineUrl() = "https://locaine.herokuapp.com/v1/"


    @Singleton
    @Provides
    @Named("randomData")
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    @Singleton
    @Provides
    @Named("locaineData")
    fun provideLocaineRetrofit(@Named("LocaineUrl") locaineUrl: String): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(locaineUrl)
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun RestDataSource(@Named("randomData") retrofit: Retrofit): RestDataSource = retrofit.create(RestDataSource::class.java)

    @Singleton
    @Provides
    fun RestLocaineDataSource(@Named("locaineData") retrofit: Retrofit): RestLocaineDataSource = retrofit.create(RestLocaineDataSource::class.java)


    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DBDataSource {
        return Room.databaseBuilder(context, DBDataSource::class.java,"user_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun userDao(db: DBDataSource): UserDao = db.userDao()


}