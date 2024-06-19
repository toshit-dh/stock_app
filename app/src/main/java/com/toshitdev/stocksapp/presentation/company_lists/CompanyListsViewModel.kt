package com.toshitdev.stocksapp.presentation.company_lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toshitdev.stocksapp.domain.repository.StockRepository
import com.toshitdev.stocksapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CompanyListStates())
    val state = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CompanyListStates()
    )


    private var searchJob: Job? = null

    init {
        getCompanyList()
    }


    fun onEvent(event: CompanyListEvents) {
        when (event) {
            CompanyListEvents.Refresh -> {
                getCompanyList(fetchFromRemote = true)
            }

            is CompanyListEvents.onSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyList()
                }
            }
        }
    }

    private fun getCompanyList(
        searchQuery: String = _state.value.searchQuery,
        fetchFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            repository.getCompanyList(
                query = searchQuery,
                fetchFromRemote = fetchFromRemote
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _state.update {
                                it.copy(
                                    companies = result.data
                                )
                            }
                        }
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}