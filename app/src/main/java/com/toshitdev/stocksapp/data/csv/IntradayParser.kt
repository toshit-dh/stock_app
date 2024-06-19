package com.toshitdev.stocksapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import com.toshitdev.stocksapp.data.mapper.toIntraday
import com.toshitdev.stocksapp.data.remote.dto.IntradayDto
import com.toshitdev.stocksapp.domain.model.Intraday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayParser @Inject constructor(): CSVParser<Intraday> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parser(
        stream: InputStream)
    : List<Intraday> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull {
                    val timestamp = it.getOrNull(0) ?: return@mapNotNull null
                    val close = it.getOrNull(4) ?: return@mapNotNull null
                    val dto =IntradayDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    )
                    dto.toIntraday()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}