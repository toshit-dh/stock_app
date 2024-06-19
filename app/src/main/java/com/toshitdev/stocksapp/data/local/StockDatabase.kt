package com.toshitdev.stocksapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompanyListEntity::class], version = 1, exportSchema = false)
abstract class StockDatabase: RoomDatabase() {
    abstract val dao: StockDao
}