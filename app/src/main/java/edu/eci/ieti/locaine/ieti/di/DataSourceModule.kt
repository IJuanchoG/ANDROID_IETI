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
import edu.eci.ieti.locaine.ieti.model.UserDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

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