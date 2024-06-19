package com.toshitdev.stocksapp.data.repository

import coil.network.HttpException
import com.toshitdev.stocksapp.data.csv.CSVParser
import com.toshitdev.stocksapp.data.local.StockDatabase
import com.toshitdev.stocksapp.data.mapper.toCompany
import com.toshitdev.stocksapp.data.mapper.toCompanyInfo
import com.toshitdev.stocksapp.data.mapper.toCompanyListEntity
import com.toshitdev.stocksapp.data.remote.StockApi
import com.toshitdev.stocksapp.domain.model.CompanyInfo
import com.toshitdev.stocksapp.domain.model.CompanyList
import com.toshitdev.stocksapp.domain.model.Intraday
import com.toshitdev.stocksapp.domain.repository.StockRepository
import com.toshitdev.stocksapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import java.net.HttpRetryException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi,
    private val db: StockDatabase,
    private val companyListParser: CSVParser<CompanyList>,
    private val intradayParser: CSVParser<Intraday>
): StockRepository  {
    private val dao = db.dao
    override suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>> {
        return flow {
            emit(Resource.Loading(true))
            val localList = dao.searchCompanyList(query)
            emit(Resource.Success(
                data = localList.map {
                    it.toCompany()
                }
            ))
            val isDbEmpty = localList.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteList = try {
                val response = stockApi.getList()
                companyListParser.parser(response.byteStream())
            }catch (e: IOException){
                e.printStackTrace()
                Resource.Error<List<CompanyList>>("Couldn't load data ${e.message}")
                null
            }catch (e: HttpRetryException){
                e.printStackTrace()
                Resource.Error<List<CompanyList>>("Couldn't load data ${e.message}")
                null
            }

            remoteList?.let { lists ->
                dao.clearCompanyList()
                dao.insertCompanyList(lists.map { it.toCompanyListEntity() })
                emit(Resource.Success(dao.searchCompanyList("").map {
                    it.toCompany()
                }))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<Intraday>> {
        return try {
            val response = stockApi.getIntradayInfo(symbol)
            Resource.Success(intradayParser.parser(response.byteStream()))
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = stockApi.getCompanyInfo(symbol).toCompanyInfo()
            Resource.Success(response)
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }catch (e: HttpException){
            e.printStackTrace()
            Resource.Error("Couldn't load data ${e.message}")
        }
    }
}