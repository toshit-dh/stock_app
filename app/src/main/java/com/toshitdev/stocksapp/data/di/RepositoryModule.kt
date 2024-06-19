package com.toshitdev.stocksapp.data.di

import com.toshitdev.stocksapp.data.csv.CSVParser
import com.toshitdev.stocksapp.data.csv.CompanyListParser
import com.toshitdev.stocksapp.data.csv.IntradayParser
import com.toshitdev.stocksapp.data.repository.StockRepositoryImpl
import com.toshitdev.stocksapp.domain.model.CompanyList
import com.toshitdev.stocksapp.domain.model.Intraday
import com.toshitdev.stocksapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCompanyListParser(
        companyListParser: CompanyListParser
    ): CSVParser<CompanyList>

    @Singleton
    @Binds
    abstract fun bindIntradayParser(
        intradayParser: IntradayParser
    ): CSVParser<Intraday>

    @Singleton
    @Binds
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

}