package com.toshitdev.stocksapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyList(
        companyListingEntities: List<CompanyListEntity>
    )

    @Query("DELETE FROM CompanyListEntity")
    suspend fun clearCompanyList()

    @Query(
        """
            SELECT * 
            FROM CompanyListEntity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyList(query: String): List<CompanyListEntity>
}