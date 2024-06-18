package com.toshitdev.stocksapp.domain.repository

import com.toshitdev.stocksapp.domain.model.CompanyList
import com.toshitdev.stocksapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>>

}