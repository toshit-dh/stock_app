package com.toshitdev.stocksapp.data.csv

import java.io.InputStream

interface CSVParser<T> {

    suspend fun parser(
        stream: InputStream
    ): List<T>

}