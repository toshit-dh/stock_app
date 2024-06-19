package com.toshitdev.stocksapp.presentation.company_lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toshitdev.stocksapp.R

@Composable
@Destination(start = true)
fun CompanyListScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                    )
                },
                maxLines = 1,
                singleLine = true,
                value = state.searchQuery,
                onValueChange = {
                    onEvent(CompanyListEvents.onSearchQueryChange(it))
                }
            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { onEvent(CompanyListEvents.Refresh) }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.companies.size) {
                        CompanyItem(
                            company = state.companies[it],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    //navigator.navigate(CompanyInfoScreenDestination(state.companies[it].symbol))
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
