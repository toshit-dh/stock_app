package com.toshitdev.stocksapp.data.repository

import com.toshitdev.stocksapp.data.local.StockDatabase
import com.toshitdev.stocksapp.data.mapper.toCompany
import com.toshitdev.stocksapp.data.remote.StockApi
import com.toshitdev.stocksapp.domain.model.CompanyList
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
    val stockApi: StockApi,
    val db: StockDatabase
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
                response.byteStream()
            }catch (e: IOException){
                e.printStackTrace()
                Resource.Error<List<CompanyList>>("Couldn't load data ${e.message}")
            }catch (e: HttpRetryException){
                e.printStackTrace()
                Resource.Error<List<CompanyList>>("Couldn't load data ${e.message}")
            }
        }
    }
}