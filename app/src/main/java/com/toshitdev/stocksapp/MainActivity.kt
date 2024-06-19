package com.toshitdev.stocksapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.toshitdev.stocksapp.ui.theme.StocksAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.toshitdev.stocksapp.presentation.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StocksAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

