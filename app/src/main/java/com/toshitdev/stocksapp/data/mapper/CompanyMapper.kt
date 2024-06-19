package com.toshitdev.stocksapp.data.mapper

import com.toshitdev.stocksapp.data.local.CompanyListEntity
import com.toshitdev.stocksapp.data.remote.dto.CompanyInfoDto
import com.toshitdev.stocksapp.domain.model.CompanyInfo
import com.toshitdev.stocksapp.domain.model.CompanyList

fun CompanyListEntity.toCompany(): CompanyList = CompanyList(
    symbol = symbol,
    name = name,
    exchange = exchange
)

fun CompanyList.toCompanyListEntity(): CompanyListEntity = CompanyListEntity(
    symbol = symbol,
    name = name,
    exchange = exchange
)

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo = CompanyInfo(
    symbol = symbol?: "",
    description = description?: "",
    name = name?: "",
    country = country?: "",
    industry = industry?: ""
)
