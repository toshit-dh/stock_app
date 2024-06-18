package com.toshitdev.stocksapp.data.mapper

import com.toshitdev.stocksapp.data.local.CompanyListEntity
import com.toshitdev.stocksapp.domain.model.CompanyList

fun CompanyListEntity.toCompany() : CompanyList{
    return CompanyList(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}

fun CompanyList.toCompanyListEntity() : CompanyListEntity{
    return CompanyListEntity(
        symbol = symbol,
        name = name,
        exchange = exchange
    )
}