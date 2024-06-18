package com.toshitdev.stocksapp.presentation.company_lists

sealed class CompanyListEvents {
    data object Refresh : CompanyListEvents()
    data class onSearchQueryChange(val query: String) : CompanyListEvents()



}