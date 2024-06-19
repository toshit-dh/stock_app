package com.toshitdev.stocksapp.presentation.company_info

import com.toshitdev.stocksapp.domain.model.CompanyInfo
import com.toshitdev.stocksapp.domain.model.Intraday

data class CompanyInfoStates(
    val stockInfo: List<Intraday> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
