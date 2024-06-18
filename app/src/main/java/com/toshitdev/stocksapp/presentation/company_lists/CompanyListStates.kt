package com.toshitdev.stocksapp.presentation.company_lists

import com.toshitdev.stocksapp.domain.model.CompanyList

data class CompanyListStates(
    val companies: List<CompanyList> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
