package com.toshitdev.stocksapp.data.di

import android.app.Application
import androidx.room.Room
import com.toshitdev.stocksapp.data.local.StockDatabase
import com.toshitdev.stocksapp.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): StockApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(StockApi.BASE_URL)
            .build()
            .create(StockApi::class.java)

    }

    @Singleton
    @Provides
    fun provideDatabase(
        context: Application
    ): StockDatabase{
        return Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "stock_db"
        ).build()
    }
}