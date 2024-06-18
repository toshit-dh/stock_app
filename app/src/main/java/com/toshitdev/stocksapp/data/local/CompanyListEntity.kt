package com.toshitdev.stocksapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val symbol: String,
    val exchange: String
)