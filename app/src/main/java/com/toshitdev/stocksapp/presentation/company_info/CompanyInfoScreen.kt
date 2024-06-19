package com.toshitdev.stocksapp.presentation.company_info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toshitdev.stocksapp.R

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Destination
fun CompanyInfoScreen(
    navigator: DestinationsNavigator,
    symbol: String,
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.error == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.company_info),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.navigateUp()
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Navigate Back")
                        }
                    }
                    )
            }
        ) {pv->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pv)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                state.company?.let { company ->
                    Text(
                        text = company.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = company.symbol,
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Industry: ${company.industry}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Country: ${company.country}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = company.description,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (state.stockInfo.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Market Summary")
                        Spacer(modifier = Modifier.height(32.dp))
                        StockChart(
                            stockInfo = state.stockInfo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .align(CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            Text(
                text = "Loading....",
                color = Color.White
            )
        } else if (state.error != null) {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

